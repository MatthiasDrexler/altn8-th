{
  description = "AltN8-TH";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/944b2aea7f0a2d7c79f72468106bc5510cbf5101";
    flake-utils.url = "github:numtide/flake-utils/c1dfcf08411b08f6b8615f7d8971a2bfa81d5e8a";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachSystem [ "x86_64-linux" ] (system:
      let
        jdkOverlay = final: prev: {
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
