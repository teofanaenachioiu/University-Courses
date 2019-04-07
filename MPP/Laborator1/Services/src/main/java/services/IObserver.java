package services;

import model.Proba;

import java.util.List;

public interface IChatObserver {
     public void inscriereParticipant(String nume, int varsta, List<Proba> listaProbe, String usernameOperator);
     public void stergeToateInregistrarile();
}
