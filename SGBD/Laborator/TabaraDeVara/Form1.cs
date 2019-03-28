using System.Configuration;
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

namespace TabaraDeVara
{
    public partial class Form1 : Form
    {
        SqlConnection conn;
        DataSet ds;
        SqlDataAdapter daPartic, daGrupe;
        BindingSource bsPartic, bsGrupe;
        SqlCommandBuilder cb;
        string connectionString;

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            connectionString = Configuration.ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
            conn = new SqlConnection(connectionString);
            daPartic = new SqlDataAdapter("SELECT * FROM Participanti", conn);
            daGrupe = new SqlDataAdapter("SELECT * FROM Grupe", conn);

            ds = new DataSet();

            daPartic.Fill(ds, "Participanti");
            
            daGrupe.Fill(ds, "Grupe");
            ds.Relations.Add("FK_Grupe_Participanti", ds.Tables["Grupe"].Columns["Gid"],
                ds.Tables["Participanti"].Columns["Gid"]);

            bsGrupe = new BindingSource();
            bsGrupe.DataSource = ds;
            bsGrupe.DataMember = "Grupe"; 

            bsPartic = new BindingSource();
            bsPartic.DataSource = bsGrupe;
            bsPartic.DataMember = "FK_Grupe_Participanti";

            dataGridViewChild.DataSource = bsPartic;
            dataGridViewParent.DataSource = bsGrupe;

            //cb = new SqlCommandBuilder(daPartic);
        }

        private void buttonSave_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    daPartic.SelectCommand.Connection = connection;
                    SqlCommandBuilder builder = new SqlCommandBuilder(daPartic);
                    daPartic.Update(ds, "Participanti");
                    ds.Tables["Participanti"].Clear();
                    daPartic.Fill(ds, "Participanti");

                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        
    }
}
