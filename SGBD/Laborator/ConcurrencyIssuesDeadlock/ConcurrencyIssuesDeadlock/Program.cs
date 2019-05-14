using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ConcurrencyIssuesDeadlock
{
    class Program
    {
        static void Deadlock()
        {          
            string connectionString = "Data Source=TEOFANA-PC; Initial Catalog=TabaraDeVara; Integrated Security=True;";
            using (var conn = new SqlConnection(connectionString))
            using (var command = new SqlCommand("tranzactia1", conn)
            {
                CommandType = CommandType.StoredProcedure
            })
            {
                conn.Open();
                command.ExecuteNonQuery();
                Console.WriteLine("TRANZACTIA 1");
            }
        }

        static void Deadlock2()
        {
            int failed = 4;
            while (failed > 0)
            {
                try
                {
                    string connectionString = "Data Source=TEOFANA-PC; Initial Catalog=TabaraDeVara; Integrated Security=True;";
                    using (var conn2 = new SqlConnection(connectionString))
                    using (var command2 = new SqlCommand("tranzactia2", conn2)
                    {
                        CommandType = CommandType.StoredProcedure
                    })
                    {
                        conn2.Open();
                        command2.ExecuteNonQuery();
                        failed = 0;
                        Console.WriteLine("TRANZACTIA 2");
                    }
                }
                catch (SqlException e)
                {
                    Console.WriteLine(e.Message);
                    Console.WriteLine("TRANZACTIA 2");
                    Console.WriteLine(failed);
                    failed--;

                    Thread t1 = new Thread(Deadlock);
                    t1.Start();
                }
            }
        }

        static void Main(string[] args)
        {
            
            Thread t1 = new Thread(Deadlock);
            
                                                            
            Thread t2 = new Thread(Deadlock2);
            
           
            t1.Start();
            t2.Start();                                    

            
            Console.ReadKey();
        }
        
    }
}
