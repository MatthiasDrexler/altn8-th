{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = (with pkgs; [
    jetbrains.jdk
    gradle
  ]);

  JETBRAINS_SDK = pkgs.jetbrains.jdk.outPath;
}
