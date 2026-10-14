param(
    [Parameter(Mandatory = $true)][string]$InputMap,
    [Parameter(Mandatory = $true)][string]$OutputMap
)
$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Drawing
$source = [Drawing.Bitmap]::new((Resolve-Path -LiteralPath $InputMap).Path)
$result = [Drawing.Bitmap]::new($source.Width, $source.Height)
try {
    for ($y = 0; $y -lt $source.Height; $y++) {
        for ($x = 0; $x -lt $source.Width; $x++) {
            $mer = $source.GetPixel($x, $y)
            # Bedrock MERS: R metalness, G emission, B roughness, A subsurface.
            # LabPBR: R perceptual smoothness, G F0/metal ID, B porosity/SSS, A emission.
            # Binary metal masks map to the generic metal ID; dielectrics use F0 = 4%.
            $reflectance = if ($mer.R -ge 128) { 255 } else { 10 }
            $emission = [int][Math]::Round($mer.G * 254.0 / 255.0)
            $result.SetPixel($x, $y, [Drawing.Color]::FromArgb($emission, (255 - $mer.B), $reflectance, 0))
        }
    }
    $result.Save([IO.Path]::GetFullPath($OutputMap), [Drawing.Imaging.ImageFormat]::Png)
} finally {
    $source.Dispose()
    $result.Dispose()
}
