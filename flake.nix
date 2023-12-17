{
  description = "AltN8-TH";

  inputs = {
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachSystem [ "x86_64-linux" ] (system:
      let
        jdkOverlay = final: prev: {
          jdk = prev.jetbrains.jdk;
        };
        overlays = [ jdkOverlay ];

        pkgs = import nixpkgs { inherit system overlays; };
      in
      {
        flakedPkgs = pkgs;

        devShell = import ./shell.nix { inherit pkgs; };
      }
    );
}
