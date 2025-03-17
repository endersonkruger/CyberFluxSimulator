package com.cyberflux.resources;
// Interface para os recursos (PC, Headset, Cadeira)
public interface Recurso {
    boolean alocar() throws InterruptedException;
    void liberar();
}
