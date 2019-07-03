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

namespace ChristmasGift
{
    public partial class Form1 : Form
    {
        SqlConnection dbConn;
        DataSet ds;
        SqlDataAdapter daParinte, daFiu;
        SqlCommandBuilder cb;
        BindingSource bsParinte, bsFiu;

        string numeBD = "ChristmasGift";
        string numeTabelaParinte = "Categories";
        string numeTabelaFiu = "Products";
        string idComunTabele = "cid";

        public Form1()
        {
            InitializeComponent();
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            daFiu.Update(ds, numeTabelaFiu);
        }

        private void GetData()
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
            ds.Relations.Add("FK_" + numeTabelaFiu + "_" + numeTabelaParinte, ds.Tables[numeTabelaParinte].Columns[idComunTabele],
                ds.Tables[numeTabelaFiu].Columns[idComunTabele]);

            bsParinte = new BindingSource();
            bsParinte.DataSource = ds; // obiect
            bsParinte.DataMember = numeTabelaParinte; // string

            bsFiu = new BindingSource();
            bsFiu.DataSource = bsParinte; // obiect
            bsFiu.DataMember = "FK_" + numeTabelaFiu + "_" + numeTabelaParinte; // string

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            GetData();
            dgvParinte.DataSource = bsParinte;
            dgvFiu.DataSource = bsFiu;
        }
    }
}
