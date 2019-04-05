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
        string connectionString;
        string numeFiu;
        string numeParinte;

        public Form1()
        {
            InitializeComponent();
        }

   

        private void Form1_Load(object sender, EventArgs e)
        {
            string title = Configuration.ConfigurationManager.AppSettings["title"];
            connectionString = Configuration.ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
            numeFiu = Configuration.ConfigurationManager.AppSettings["fiu"];
            numeParinte = Configuration.ConfigurationManager.AppSettings["parinte"];
            string selectFiu = Configuration.ConfigurationManager.AppSettings["selectFiu"];
            string selectParinte = Configuration.ConfigurationManager.AppSettings["selectParinte"];
            string idParinte = Configuration.ConfigurationManager.AppSettings["idParinte"];
            List<string> eticheteFiu= new List<string>(Configuration.ConfigurationManager.AppSettings["eticheteFiu"].Split(','));

            this.Text = title;

            conn = new SqlConnection(connectionString);

            daPartic = new SqlDataAdapter(selectFiu, conn);
            daGrupe = new SqlDataAdapter(selectParinte, conn);

            ds = new DataSet();
            daPartic.Fill(ds, numeFiu);
            daGrupe.Fill(ds, numeParinte);
         
            ds.Relations.Add("FK_Tabele", ds.Tables[numeParinte].Columns[idParinte],
            ds.Tables[numeFiu].Columns[idParinte]);

            bsGrupe = new BindingSource();
            bsGrupe.DataSource = ds;
            bsGrupe.DataMember = numeParinte; 

            bsPartic = new BindingSource();
            bsPartic.DataSource = bsGrupe;
            bsPartic.DataMember = "FK_Tabele";

            dataGridViewChild.DataSource = bsPartic;
            dataGridViewParent.DataSource = bsGrupe;

            labelFiu.Text = numeFiu;
            labelParinte.Text = numeParinte;

            foreach (string column in eticheteFiu)
            {
                //TextBox textBox = (TextBox)panelTextBoxes.Controls[column];
                Label lbl = new Label();
                lbl.Text = column;
                panelTextBoxes.Controls.Add(lbl);

                /*TextBox txt = new TextBox();
                panelTextBoxes.Controls.Add(txt);*/

                
            }

        }

        private void buttonSave_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    daPartic.SelectCommand.Connection = connection;
                    SqlCommandBuilder builder = new SqlCommandBuilder(daPartic);
                    daPartic.Update(ds, numeFiu);
                    ds.Tables[numeFiu].Clear();
                    daPartic.Fill(ds, numeFiu);

                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        
    }
}
