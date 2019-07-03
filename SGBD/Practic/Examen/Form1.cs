using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Examen
{
    public partial class Form1 : Form
    {
        SqlConnection dbConn;
        DataSet ds;
        SqlDataAdapter daParinte, daFiu;
        SqlCommandBuilder cb;
        BindingSource bsParinte, bsFiu;

        string numeBD = "Aprovizionare";
        string numeTabelaParinte = "TipuriLegume";
        string numeTabelaFiu = "Legume";

        private void Initializare()
        {
            labelTitlu.Text = numeBD;
            labelParinte.Text = numeTabelaParinte;
            labelFiu.Text = numeTabelaFiu;

            dbConn = new SqlConnection("Data Source=TEOFANA-PC; " +
               "Initial Catalog=" + numeBD + "; Integrated Security=True;");
            ds = new DataSet();
            daParinte = new SqlDataAdapter("SELECT * FROM " + numeTabelaParinte, dbConn);
            daFiu = new SqlDataAdapter("SELECT * FROM " + numeTabelaFiu, dbConn);
            cb = new SqlCommandBuilder(daFiu);

            daParinte.Fill(ds, numeTabelaParinte);
            daFiu.Fill(ds, numeTabelaFiu);

            string cheia_straina = "FK_" + numeTabelaFiu + "_" + numeTabelaParinte;

            ds.Relations.Add( cheia_straina, ds.Tables[numeTabelaParinte].Columns["Tid"],
                ds.Tables[numeTabelaFiu].Columns["Tid"]);

            bsParinte = new BindingSource();
            bsParinte.DataSource = ds; 
            bsParinte.DataMember = numeTabelaParinte;

            bsFiu = new BindingSource();
            bsFiu.DataSource = bsParinte;
            bsFiu.DataMember = cheia_straina;

        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            daFiu.Update(ds, numeTabelaFiu);
        }

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            Initializare();
            dgvParinte.DataSource = bsParinte;
            dgvFiu.DataSource = bsFiu;
        }
    }
}
