{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = (with pkgs; [
    jdk
    jetbrains.jcef
  ]);

  shellHook =
  ''
    export JETBRAINS_SDK_PATH="${pkgs.jdk.outPath}"
  '';
}
