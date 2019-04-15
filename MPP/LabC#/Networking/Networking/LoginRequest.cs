using Concurs.model;
using System;
namespace Networking
{

	[Serializable]
    public class LoginRequest : Request
	{
		private User user;

		public LoginRequest(User user)
		{
			this.user = user;
		}

		public virtual User User
		{
			get
			{
				return user;
			}
		}
	}

}