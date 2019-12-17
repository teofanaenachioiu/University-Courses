package com.exemple.app.controller;

import com.exemple.app.domain.Loc;
import com.exemple.app.domain.Vanzare;
import com.exemple.app.repository.LocRepository;
import com.exemple.app.repository.VanzareRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Worker implements Callable<Vanzare> {
    private VanzareRepository vanzareRepository;
    private LocRepository locRepository;
    private List<Loc> locuriSpectacol;
    private Vanzare vanzare;

    public Worker(VanzareRepository vanzareRepository,
                  LocRepository locRepository,
                  Vanzare vanzare,
                  List<Loc> locuriSpectacol) {
        this.vanzareRepository = vanzareRepository;
        this.locRepository = locRepository;
        this.locuriSpectacol = locuriSpectacol;
        this.vanzare = vanzare;
    }

    @Override
    public Vanzare call() {
        for (Loc loc : locuriSpectacol) {
            synchronized (locRepository) {
                if (locRepository.isLocOcupat(loc.getNumar(), vanzare.getSpectacolMapat().getId()) != 0) {
                    return null;
                }
            }
        }

        vanzare.setListaLocuri(locuriSpectacol);
        synchronized (vanzareRepository) {
            synchronized (locRepository) {
                Vanzare vanzareee = vanzareRepository.save(vanzare);
                locRepository.saveAll(vanzare.getListaLocuri());
                return vanzareee;
            }
        }
    }
}