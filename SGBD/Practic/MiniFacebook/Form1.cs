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

namespace MiniFacebook
{
    public partial class Form1 : Form
    {
        SqlConnection dbConn;
        DataSet ds;
        SqlDataAdapter daUtilizatori, daPostrari;
        SqlCommandBuilder cb;
        BindingSource bsUtilizatori, bsPostari;

        public Form1()
        {
            InitializeComponent();
        }

        private void btnUpdateBd_Click(object sender, EventArgs e)
        {
            daPostrari.Update(ds, "Postari");
        }

        private void GetData()
        {
            dbConn = new SqlConnection("Data Source=TEOFANA-PC; " +
                "Initial Catalog=MiniFacebook; Integrated Security=True;");
            ds = new DataSet();
            daUtilizatori = new SqlDataAdapter("SELECT * FROM Utilizatori", dbConn);
            daPostrari = new SqlDataAdapter("SELECT * FROM Postari", dbConn);
            cb = new SqlCommandBuilder(daPostrari);
            daUtilizatori.Fill(ds, "Utilizatori"); //Poate fi alt nume, nu neaparat Utilizatori
            daPostrari.Fill(ds, "Postari");
            ds.Relations.Add("FK_Postari_Utilizatori",ds.Tables["Utilizatori"].Columns["CodU"], 
                ds.Tables["Postari"].Columns["CodU"]);

            bsUtilizatori = new BindingSource();
            bsUtilizatori.DataSource = ds; // obiect
            bsUtilizatori.DataMember = "Utilizatori"; // string

            bsPostari = new BindingSource();
            bsPostari.DataSource = bsUtilizatori; // obiect
            bsPostari.DataMember = "FK_Postari_Utilizatori"; // string
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            GetData();
            dgvUtilizatori.DataSource = bsUtilizatori;
            dgvPostari.DataSource = bsPostari;

        }
    }
}
