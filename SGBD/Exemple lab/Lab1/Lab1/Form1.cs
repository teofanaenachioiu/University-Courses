using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace Lab1
{
    public partial class Form1 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=TEOFANA-PC; " +
            "Initial Catalog=Laborator; " +
            "Integrated Security=True;");
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();
        BindingSource bs = new BindingSource(); 

        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            //conectare
            da.SelectCommand = new SqlCommand(
                "SELECT * FROM Produs", cs);
            ds.Clear();
            da.Fill(ds);
            dataGridView.DataSource = ds.Tables[0];
            bs.DataSource = ds.Tables[0];//pentru a accesa o anumita celula/rand/coloana
        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                //adaugare
                da.InsertCommand =
                    new SqlCommand("INSERT INTO Produs (denumire,pret,cantitate) VALUES(@d,@p,@c)", cs);
                da.InsertCommand.Parameters.Add("@d", SqlDbType.VarChar).Value = textBox1.Text;
                da.InsertCommand.Parameters.Add("@p", SqlDbType.Int).Value = Int32.Parse(textBox2.Text);
                da.InsertCommand.Parameters.Add("@c", SqlDbType.Int).Value = Int32.Parse(textBox3.Text);
                cs.Open();//deschid conexiunea pentru ca vreau ca datele sa fie inserate in baza de date
                da.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Adaugat!");

                //pentru ca sa se vada modificarile
                da.SelectCommand = new SqlCommand(
                    "SELECT * FROM Produs", cs);
                ds.Clear();
                da.Fill(ds);
                dataGridView.DataSource = ds.Tables[0];

                //inchid conexiunea
                cs.Close();
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
            
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void label3_Click(object sender, EventArgs e)
        {

        }
    }
}
