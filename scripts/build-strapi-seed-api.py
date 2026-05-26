import json
import subprocess
from pathlib import Path

root = Path(__file__).resolve().parents[1]
raw = subprocess.check_output(
    ["git", "show", "HEAD:src/main/resources/messages_es.properties"],
    cwd=root,
    text=True,
    encoding="utf-8",
)
d: dict[str, str] = {}
for line in raw.splitlines():
    line = line.strip()
    if not line or "=" not in line:
        continue
    k, v = line.split("=", 1)
    d[k] = v
api = []
for k, v in d.items():
    if k.endswith(".technical"):
        code = k[: -len(".technical")]
        api.append({"codigo": code, "textoUsuario": d.get(code, ""), "textoTecnico": v})
api.sort(key=lambda x: x["codigo"])
out = root / "docs" / "strapi" / "seed-mensaje-apis.json"
out.write_text(json.dumps(api, ensure_ascii=False, indent=2), encoding="utf-8")
print("written", out, "entries", len(api))
