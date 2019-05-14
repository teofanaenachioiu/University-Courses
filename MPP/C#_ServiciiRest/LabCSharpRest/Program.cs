using Concurs.model;
using System;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;

namespace LabCSharpRest
{
    class Program
    {
        static HttpClient client = new HttpClient();
        static Uri url = new Uri("http://localhost:8080/concurs");

        public static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            RunAsync().Wait();
        }


        static async Task RunAsync()
        {
            client.BaseAddress = url;
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

            // create
            Console.WriteLine("---------- CREATE -----------");
            Proba proba = new Proba("Dans", "CATEGORIE_12_15");

            Console.WriteLine("Proba de adaugat: {0}", proba);
            Proba resultCreate = await PostProbaAsync(proba);
            Console.WriteLine("Proba adaugata: {0}", resultCreate);

            // get all
            Console.WriteLine("---------- GET ALL ----------");
            Console.WriteLine("Lista de probe");
            Proba[] probe = await GetProbeListAsync(url + "/probe");
            foreach(Proba pr in probe)
            {
                Console.WriteLine(pr);
            }

            // find by id
            Console.WriteLine("--------- FIND BY ID ---------");
            int lastID = probe[probe.Length - 1].Id;
            proba.Id = lastID;
            Console.WriteLine("Proba cu id-ul " + lastID + " este: " + await GetProbaAsync(url + "/probe/" + lastID));

            // update
            Console.WriteLine("---------- UPDATE ----------");
            Console.WriteLine("Proba inainte de update: " + proba);
            proba.Denumire = "Dansssss";
            Console.WriteLine("Proba dupa update: "+ await PutProbaAsync(proba));


            // delete
            Console.WriteLine("---------- DELETE ----------");
            Console.WriteLine("Sterg proba cu id-ul: " + lastID);
            await DeleteProbaAsync(lastID);

            // get all
            Console.WriteLine("---------- GET ALL ----------");
            Console.WriteLine("Lista de probe");
            probe = await GetProbeListAsync(url + "/probe");
            foreach (Proba pr in probe)
            {
                Console.WriteLine(pr);
            }

        }

        static async Task<Proba> PostProbaAsync(Proba p)
        {
            Proba proba = null;
            HttpResponseMessage response = await client.PostAsJsonAsync("", p);
            if (response.IsSuccessStatusCode)
            {
                proba = await response.Content.ReadAsAsync<Proba>();
            }
            return proba;
        }

        static async Task<Proba> DeleteProbaAsync(int id)
        {
            Proba proba = null;
            HttpResponseMessage response = await client.DeleteAsync(url + "/probe/"+id);
            if (response.IsSuccessStatusCode)
            {
                proba = await response.Content.ReadAsAsync<Proba>();
            }
            else
            {
                Console.WriteLine(response.StatusCode);
            }
            return proba;
        }

        static async Task<Proba> PutProbaAsync(Proba p)
        {
            Proba product = null;
            HttpResponseMessage response = await client.PutAsJsonAsync(url + "/probe/" + p.Id, p);

            if (response.IsSuccessStatusCode)
            {
                product = await response.Content.ReadAsAsync<Proba>();
            }
            else
            {
                Console.WriteLine(response.StatusCode);
            }
            return product;
        }


        static async Task<Proba> GetProbaAsync(string path)
        {
            Proba product = null;
            HttpResponseMessage response = await client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                product = await response.Content.ReadAsAsync<Proba>();
            }
            return product;
        }

        static async Task<Proba[]> GetProbeListAsync(string path)
        {
            Proba[] probe = null;
            HttpResponseMessage response = await client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                probe = await response.Content.ReadAsAsync<Proba[]>();
            }
            return probe;
        }
    }
}