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

exception MyAppExecption {
  1: string message
}
service ConcursService{
	void login(1:string username,2:string password) throws (1: MyAppExecption e);
	User cauta(1:string username);
	void logout(1:User user) throws (1: MyAppExecption e);
}

