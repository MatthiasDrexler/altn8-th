{ pkgs ? import <nixpkgs> {} }:

let
    pathToDownloadedJRE = "~/.gradle/caches/modules-2/files-2.1/com.jetbrains/jbre/jbr_jcef-17.0.6-linux-x64-b469.82/extracted/jbr_jcef-17.0.6-x64-b469";
in
pkgs.mkShell {
  buildInputs = (with pkgs; [
    jdk
    gradle
  ]);

  shellHook =
  ''
    ln -sf ${pkgs.jdk.outPath} ${pathToDownloadedJRE}
  '';
}
