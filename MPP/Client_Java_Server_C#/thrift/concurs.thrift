namespace java org.teofana.concurs
namespace csharp Concurs

enum TipUser{
	ADMIN=1,
	OPERATOR=2
}

struct User {
	1: string username
	2: string password
	3: TipUser tipUser
}

struct Proba {
	1: i32 id
	2: string denumire
	3: string categorie
}

struct ProbaDTO {
	1: i32 id
	2: string denumire
	3: string categorie
	4: i32 nrParticipanti
}

struct Participant{
	1: i32 id
	2: string nume
	3: i32 varsta
}

exception MyAppException {
	1: string message
}
service ConcursService{
	i32 login(1:string username,2:string password) throws (1: MyAppException e);
	User cauta(1:string username);
	void logout(1:User user) throws (1: MyAppException e);
	
	list<Participant> listaParticipanti();
    list<string> listaProbeNume();
    list<string> listaCategorii();
    list<Participant> filtreazaParticipantiKeyword(1:string proba, 2:string categorie);
    list<Proba> listaProbe();
    list<ProbaDTO> listaProbeDTO();
	
	void inscriereParticipant(1: string nume, 2:i32 varsta, 3:list<Proba> listaProbe, 4:string usernameOperator) throws (1:MyAppException e);
	
}

