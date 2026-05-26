# Comprueba: Strapi REST (colección UI) y Spring (catálogo que usa el front).
# No imprime STRAPI_API_TOKEN; solo códigos HTTP y un recorte del cuerpo.
#
# Uso (recomendado, secretos en Infisical):
#   infisical run --env=dev -- powershell -NoProfile -File .\scripts\verify-strapi-spring.ps1
#
# Uso (variables ya en la sesión):
#   powershell -NoProfile -File .\scripts\verify-strapi-spring.ps1
#
param(
    [string] $SpringBaseUrl = "http://127.0.0.1:8080/uco-parking"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

function Invoke-GetCurl([string] $Url, [switch] $BearerIfStrapiToken, [switch] $StrapiV4Format) {
    $curlArgs = [System.Collections.Generic.List[string]]::new()
    $curlArgs.Add("-sS")
    $curlArgs.Add("-w")
    $curlArgs.Add("`nHTTP_CODE:%{http_code}")
    $curlArgs.Add("-o")
    $curlArgs.Add("-")
    if ($StrapiV4Format) {
        $curlArgs.Add("-H")
        $curlArgs.Add("Strapi-Response-Format: v4")
    }
    if ($BearerIfStrapiToken -and $env:STRAPI_API_TOKEN -and $env:STRAPI_API_TOKEN.Trim().Length -gt 0) {
        $curlArgs.Add("-H")
        $curlArgs.Add("Authorization: Bearer $($env:STRAPI_API_TOKEN.Trim())")
    }
    $curlArgs.Add($Url)
    $prevEa = $ErrorActionPreference
    $ErrorActionPreference = "SilentlyContinue"
    try {
        return (& curl.exe @($curlArgs.ToArray()) 2>&1 | Out-String)
    } finally {
        $ErrorActionPreference = $prevEa
    }
}

$base = $env:STRAPI_URL
if ([string]::IsNullOrWhiteSpace($base)) {
    $base = "http://127.0.0.1:1337"
}
$base = $base.TrimEnd('/')

$uiSlug = $env:STRAPI_COLLECTION_UI
if ([string]::IsNullOrWhiteSpace($uiSlug)) {
    $uiSlug = "mensaje-uis"
}

$strapiUi = "$base/api/$uiSlug" + "?pagination%5BpageSize%5D=3"
Write-Host "1) Strapi GET .../$uiSlug (Strapi 5: sin publicationState; opcional header v4 en Spring)"
$r1 = Invoke-GetCurl $strapiUi -BearerIfStrapiToken -StrapiV4Format
$max = [Math]::Min(800, $r1.Length)
if ($max -gt 0) {
    Write-Host $r1.Substring(0, $max)
}
if ($r1 -notmatch "HTTP_CODE:200") {
    Write-Host "   -> Revisa STRAPI_URL, STRAPI_COLLECTION_UI, token o permisos Public (docs/STRAPI.md)." -ForegroundColor Yellow
}

$catalogo = "$SpringBaseUrl/api/v1/catalogo/textos-ui"
Write-Host ""
Write-Host "2) Spring GET .../catalogo/textos-ui"
$r2 = Invoke-GetCurl $catalogo
$max2 = [Math]::Min(800, $r2.Length)
if ($max2 -gt 0) {
    Write-Host $r2.Substring(0, $max2)
}
if ($r2 -notmatch "HTTP_CODE:200") {
    Write-Host "   -> Arranca Spring en 8080 con context-path /uco-parking (p. ej. run-spring-dev.ps1)." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Si ambos HTTP_CODE son 200, Strapi responde y Spring expone el mapa UI al front."
