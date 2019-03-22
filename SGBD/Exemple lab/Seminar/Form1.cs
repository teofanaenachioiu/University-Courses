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

namespace Seminar
{
    public partial class Form1 : Form
    {
        SqlConnection conn;
        DataSet ds;
        SqlDataAdapter daPartic, daGrupe;
        BindingSource bsPartic, bsGrupe;
        SqlCommandBuilder cb;

        private void button1_Click(object sender, EventArgs e)
        {
            daPartic.Update(ds, "Participanti");
            //Sql command bulider construieste automat comenzile
            
        }

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load_1(object sender, EventArgs e)
        {
            /*conn = new SqlConnection("Data Source=TEOFANA-PC; " +
              "Initial Catalog=Laborator; " +
              "Integrated Security=True;");
           da = new SqlDataAdapter("SELECT * FROM Produs",conn);
           ds = new DataSet();
           da.Fill(ds, "Produs");*/

            /*textBox1.DataBindings.Add("Text", ds, "Produs.pid");
            textBox2.DataBindings.Add("Text", ds, "Produs.denumire");

            comboBox1.DataSource = ds;
            comboBox1.DisplayMember = "Produs.pid";*/

            /*BindingSource bs = new BindingSource();
            bs.DataSource = ds;
            bs.DataMember = "Produs";
            textBox1.DataBindings.Add("Text", bs, "pid");
            textBox2.DataBindings.Add("Text", bs, "denumire");

            comboBox1.DataSource = bs;
            comboBox1.DisplayMember = "pid";*/


            conn = new SqlConnection("Data Source=TEOFANA-PC; " +
               "Initial Catalog=TabaraDeVara; " +
               "Integrated Security=True;");
            daPartic = new SqlDataAdapter("SELECT * FROM Participanti", conn);
            daGrupe = new SqlDataAdapter("SELECT * FROM Grupe", conn);
            ds = new DataSet();
            daPartic.Fill(ds, "Participanti");
            cb = new SqlCommandBuilder(daPartic);

            daGrupe.Fill(ds, "Grupe");
            ds.Relations.Add("FK_Grupe_Participanti", ds.Tables["Grupe"].Columns["Gid"],
                ds.Tables["Participanti"].Columns["Gid"]);

            bsGrupe = new BindingSource();
            bsGrupe.DataSource = ds;
            bsGrupe.DataMember = "Grupe"; // nume local

            bsPartic = new BindingSource();
            bsPartic.DataSource = bsGrupe;
            bsPartic.DataMember = "FK_Grupe_Participanti";

            dataGridView1.DataSource = bsPartic;
            dataGridView2.DataSource = bsGrupe;
        }
    }
}
