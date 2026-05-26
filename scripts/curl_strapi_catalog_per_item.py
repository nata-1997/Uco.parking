#!/usr/bin/env python3
"""
Ejecuta curl (GET) contra Strapi por cada fila de los seeds locales:
  - docs/strapi/seed-mensaje-apis.json  -> /api/mensaje-apis?filters[codigo][$eq]=...
  - docs/strapi/seed-mensaje-uis.json    -> /api/mensaje-uis?filters[clave][$eq]=...

Variables de entorno:
  STRAPI_URL             Base URL (default: http://127.0.0.1:1337)
  STRAPI_API_TOKEN       Opcional: Bearer para APIs no públicas
  STRAPI_COLLECTION_API  Slug plural (default: mensaje-apis)
  STRAPI_COLLECTION_UI   Slug plural (default: mensaje-uis)
"""

from __future__ import annotations

import json
import os
import subprocess
import sys
import urllib.parse
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def load_json(path: Path) -> list:
    data = json.loads(path.read_text(encoding="utf-8"))
    if not isinstance(data, list):
        raise SystemExit(f"{path}: se esperaba una lista JSON")
    return data


def curl_get(url: str, token: str | None) -> tuple[int, str]:
    cmd = ["curl", "-sS", "-o", "-", "-w", "\n__HTTP_STATUS__:%{http_code}", url]
    if token:
        cmd[1:1] = ["-H", f"Authorization: Bearer {token}"]
    try:
        out = subprocess.check_output(cmd, stderr=subprocess.STDOUT, timeout=60)
    except FileNotFoundError:
        print("ERROR: no se encontró `curl` en el PATH.", file=sys.stderr)
        sys.exit(2)
    except subprocess.CalledProcessError as e:
        return -1, (e.output.decode("utf-8", errors="replace") if e.output else str(e))
    text = out.decode("utf-8", errors="replace")
    if "__HTTP_STATUS__:" in text:
        body, _, status_part = text.rpartition("__HTTP_STATUS__:")
        try:
            code = int(status_part.strip())
        except ValueError:
            code = -1
        return code, body.strip()
    return -1, text.strip()


def filter_url(base: str, collection: str, field: str, value: str) -> str:
    q = urllib.parse.urlencode({f"filters[{field}][$eq]": value})
    return f"{base}/api/{collection}?{q}"


def has_strapi_data(body: str) -> bool:
    try:
        j = json.loads(body)
    except json.JSONDecodeError:
        return False
    data = j.get("data")
    if data is None:
        return False
    if isinstance(data, list):
        return len(data) > 0
    return True


def main() -> int:
    import argparse

    p = argparse.ArgumentParser(description="curl GET por cada fila de los seeds de Strapi")
    p.add_argument(
        "--print-curl",
        action="store_true",
        help="Solo imprime las lineas curl (no ejecuta).",
    )
    args = p.parse_args()

    base = os.environ.get("STRAPI_URL", "http://127.0.0.1:1337").rstrip("/")
    token = (os.environ.get("STRAPI_API_TOKEN") or "").strip() or None
    col_api = os.environ.get("STRAPI_COLLECTION_API", "mensaje-apis").strip().strip("/")
    col_ui = os.environ.get("STRAPI_COLLECTION_UI", "mensaje-uis").strip().strip("/")

    api_seed = ROOT / "docs" / "strapi" / "seed-mensaje-apis.json"
    ui_seed = ROOT / "docs" / "strapi" / "seed-mensaje-uis.json"

    if args.print_curl:
        auth = f' -H "Authorization: Bearer {token}"' if token else ""
        for label, path, collection, field, key_fn in (
            ("Mensaje API", api_seed, col_api, "codigo", lambda r: r.get("codigo", "")),
            ("Mensaje UI", ui_seed, col_ui, "clave", lambda r: r.get("clave", "")),
        ):
            print(f"# --- {label} ---")
            for row in load_json(path):
                val = str(key_fn(row)).strip()
                if not val:
                    continue
                url = filter_url(base, collection, field, val)
                print(f'curl -sS{auth} "{url}"')
            print()
        return 0

    with_data = 0
    problems = 0

    print(f"STRAPI_URL={base}\n")

    for col in (col_api, col_ui):
        probe = f"{base}/api/{col}?" + urllib.parse.urlencode({"pagination[pageSize]": "1"})
        code, body = curl_get(probe, token)
        print(f"Prueba listado: GET /api/{col}?pagination[pageSize]=1 -> HTTP {code}")
        if code == 404:
            print(
                f"  (Si es 404, el tipo en Strapi no expone ese slug REST. Ajusta STRAPI_COLLECTION_API / UI o el nombre del tipo.)"
            )
        elif code != 200:
            print(f"  {body[:200]}...")
        print()

    for label, path, collection, field, key_fn in (
        ("Mensaje API", api_seed, col_api, "codigo", lambda r: r.get("codigo", "")),
        ("Mensaje UI", ui_seed, col_ui, "clave", lambda r: r.get("clave", "")),
    ):
        rows = load_json(path)
        print(f"=== {label} ({len(rows)} filas en seed) -> GET /api/{collection}?filters[{field}][$eq]=... ===\n")
        for i, row in enumerate(rows, 1):
            val = str(key_fn(row)).strip()
            if not val:
                print(f"  [{i}] SKIP fila sin {field}: {row!r}")
                problems += 1
                continue
            url = filter_url(base, collection, field, val)
            code, body = curl_get(url, token)
            short = body[:160].replace("\n", " ") + ("..." if len(body) > 160 else "")
            if code == 200 and has_strapi_data(body):
                with_data += 1
                print(f"  [{i}] HTTP {code}  {field}={val!r}  OK")
            else:
                problems += 1
                if code != 200:
                    print(f"  [{i}] HTTP {code}  {field}={val!r}")
                else:
                    print(f"  [{i}] HTTP {code}  {field}={val!r}  -> sin datos (importar en Strapi o revisar UID/slug)")
                if code != 200 and short:
                    print(f"       {short}")
        print()

    print(f"Resumen: {with_data} peticiones con al menos un documento; {problems} fallos o vacios.")
    return 0 if problems == 0 else 1


if __name__ == "__main__":
    sys.exit(main())
