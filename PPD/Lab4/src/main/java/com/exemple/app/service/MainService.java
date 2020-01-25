package com.exemple.app.service;

import com.exemple.app.controller.Worker;
import com.exemple.app.domain.Loc;
import com.exemple.app.domain.Spectacol;
import com.exemple.app.domain.SpectacolInfo;
import com.exemple.app.domain.Vanzare;
import com.exemple.app.repository.LocRepository;
import com.exemple.app.repository.SpectacolRepository;
import com.exemple.app.repository.VanzareRepository;
import com.exemple.app.utils.CsvAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Service
public class MainService {
    private int MAXITERATII = 2;
    private LocRepository locRepository;
    private SpectacolRepository spectacolRepository;
    private VanzareRepository vanzareRepository;

    private static final int NTHREDS = 8;
    private ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
    private List<Future<Vanzare>> list = new ArrayList<>();

    @Autowired
    public MainService(SpectacolRepository spectacolRepository, LocRepository locRepository, VanzareRepository vanzareRepository) {
        this.locRepository = locRepository;
        this.spectacolRepository = spectacolRepository;
        this.vanzareRepository = vanzareRepository;
    }

    public List<Integer> getLocuriLibere(int idSpectacol) {
        List<Integer> lista = SpectacolInfo.listaLocuriCatg1;
        lista.addAll(SpectacolInfo.listaLocuriCatg2);
        lista.addAll(SpectacolInfo.listaLocuriCatg3);
        synchronized (locRepository) {
            for (Integer nrScaun : locRepository.getLocuriOcupateLaSpectacol(idSpectacol)) {
                lista.remove(nrScaun);
            }
        }
        return lista;
    }

    public List<Integer> getIdSpectacole() {
        List<Integer> lista = new ArrayList<>();
        synchronized (spectacolRepository) {
            for (Spectacol s : spectacolRepository.findAll()) {
                lista.add(s.getId());
            }
        }
        return lista;
    }


    public boolean adaugaVanzare(Integer idSpectacol, Integer numarBilete, List<Integer> locuri) throws ExecutionException, InterruptedException {
        List<Loc> locuriSpectacol = new ArrayList<>();
        for (Integer integer : locuri) {
            Loc loc = SpectacolInfo.getLoc(integer);
            if (loc != null) {
                locuriSpectacol.add(loc);
            }
        }

        Optional<Spectacol> spectacolOptional = spectacolRepository.findById(idSpectacol);
        if (!spectacolOptional.isPresent()) {
            return false;
        }

        Vanzare vanzare = Vanzare.builder()
                .data(System.currentTimeMillis())
                .numarBilete(numarBilete)
                .spectacolMapat(spectacolOptional.get())
                .build();

        for (Loc loc : locuriSpectacol) {
            loc.setVanzareMapata(vanzare);
        }



        Callable<Vanzare> worker = new Worker(vanzareRepository, locRepository, vanzare, locuriSpectacol);
        Future<Vanzare> submit = executor.submit(worker);



        boolean isAdded = submit.get() != null;

        synchronized (list) {
            if (isAdded) {
                list.add(submit);
            }
        }

        CompletableFuture
                .runAsync(this::workerRaports)
                .join();

        return isAdded;
    }

    private void workerRaports() {
        if (list.size() % MAXITERATII == 0) {
            System.out.println("Raport Periodic");
            writeBileteRaport();
            writeVanzariRaport();
            writeSoldRaport();
            System.out.println("Raport incheiat");
            list.clear();
            System.out.println("Am golit lista");

        }
    }

    private void writeVanzariRaport() {
        List<String[]> list = new ArrayList<>();
        String[] dataa = {"data_vanzare", "ID_spectacol", "numar_bilete", "lista_locurilor"};
        list.add(dataa);

        for (Vanzare vanzare : vanzareRepository.findAll()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String vanzareDate = formatter.format(vanzare.getData());

            String spectacolId = vanzare.getSpectacolMapat().getId().toString();

            String numarBilete = String.valueOf(vanzare.getNumarBilete());
            for (Loc loc : vanzare.getListaLocuri()) {
                String nrScaun = String.valueOf(loc.getNumar());
                String[] data = {vanzareDate, spectacolId, numarBilete, nrScaun};
                list.add(data);
            }
        }
        CsvAdd.addData("vanzari.csv", list);
    }

    private void writeSoldRaport() {
        List<String[]> list = new ArrayList<>();
        String[] dataa = {"ID_spectacol", "sold"};
        list.add(dataa);

        for (Spectacol spectacol : spectacolRepository.findAll()) {

            String spectacolId = spectacol.getId().toString();

            String sold = String.valueOf(vanzareRepository.getSoldSpectacol(spectacol.getId()));

            String[] data = {spectacolId, sold};
            list.add(data);
        }
        CsvAdd.addData("solduri.csv", list);
    }

    private void writeBileteRaport() {
        List<String[]> list = new ArrayList<>();
        String[] dataa = {"data", "ID_spectacol", "lista_locuri_vandute"};
        list.add(dataa);
        for (Spectacol spectacol : spectacolRepository.findAll()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String spectacolDate = formatter.format(spectacol.getData());

            String spectacolId = spectacol.getId().toString();

            List<Integer> lista = locRepository.getLocuriOcupateLaSpectacol(spectacol.getId());
            System.out.println("Locuri ocupate la spectacolul " + spectacol.getId() + ": " + lista.size());
            for (Integer loc : lista) {
                String[] data = {spectacolDate, spectacolId, String.valueOf(loc)};
                list.add(data);
            }
        }
        CsvAdd.addData("bilete.csv", list);
    }
}
