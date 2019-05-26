package br.unisinos.parthenos.injector.enumeration;

public enum Status {
  SUCCESS(0),
  ABORTED(1),
  EXCEPTION(2),
  UNKNOWN(3);

  private int exitStatus;

  Status(int exitStatus) {
    this.exitStatus = exitStatus;
  }

  public int getExitStatus() {
    return exitStatus;
  }
}
