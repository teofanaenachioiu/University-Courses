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
        string idParinte;
        string idFiu;
        List<string> coloaneFiu;
        string selectFiu;
        string selectParinte;
        public Form1()
        {
            InitializeComponent();
        }

        private void dataGridViewChild_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            List<string> coloaneFiu = new List<string>(Configuration.ConfigurationManager.AppSettings["numeColoaneFiu"].Split(','));
            if (e.RowIndex >= 0)
            {
                //gets a collection that contains all the rows
                DataGridViewRow row = this.dataGridViewChild.Rows[e.RowIndex];
                //populate the textbox from specific value of the coordinates of column and row.
                int count = 0;
                foreach (string column in coloaneFiu)
                {
                    TextBox textBox = (TextBox)panelTextBoxes.Controls[column];

                    textBox.Text = row.Cells[count].Value.ToString();

                    if (column == idParinte)
                    {
                        textBox.ReadOnly = false;
                        textBox.Enabled = true;
                    }
                    if (column == idFiu)
                    {
                        textBox.ReadOnly = false;
                        textBox.Enabled = true;
                    }
                    count += 1;
                }
            }
        }

        private void dataGridViewParent_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                DataGridViewRow row = this.dataGridViewParent.Rows[e.RowIndex];
                TextBox textBox = (TextBox)panelTextBoxes.Controls[idParinte];
                textBox.Text = row.Cells[0].Value.ToString();
                textBox.ReadOnly = true;
                textBox.Enabled = false;
            }
        }

        private void buttonReset_Click(object sender, EventArgs e)
        {
            clearData();
        }

        private void clearData()
        {
            int count = 1;
            foreach (string column in coloaneFiu)
            {
                TextBox textBox = (TextBox)panelTextBoxes.Controls[column];

                textBox.Text = null;
                count += 1;
            }
        }

        private void Save()
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
                    showData();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            List<string> ColumnNamesUpdateParameters
                 = new List<string>(Configuration.ConfigurationManager
                 .AppSettings["ColumnNamesUpdateParameters"].Split(','));
            try
            {
                TextBox textBox = (TextBox)panelTextBoxes.Controls[idFiu];
                string sql= "UPDATE " + numeFiu +"("+ coloaneFiu +")" +
                    " VALUES ("+ColumnNamesUpdateParameters +") WHERE "+idFiu +"=" + textBox.Text;
                conn.Open();

                using (SqlCommand cmd = new SqlCommand(sql, conn))
                {
                    foreach (string column in coloaneFiu)
                    {
                        if (column != idFiu)
                        {
                            TextBox textBox1 = (TextBox)panelTextBoxes.Controls[column];
                            cmd.Parameters.AddWithValue("@" + column, textBox1.Text);
                        }  
                    }
                    cmd.ExecuteNonQuery();
                    ds.Clear();
                    daPartic.Fill(ds);
                    conn.Close();
                    showData();
                    MessageBox.Show("Row updated !! ");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroareeeeeeeee! ");
                MessageBox.Show(ex.Message);
            }
        }

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            try
            {
                TextBox textBox = (TextBox)panelTextBoxes.Controls[idFiu];
                SqlCommand cmd = new SqlCommand("DELETE FROM " +
                    numeFiu + " WHERE " + idFiu + "=" + textBox.Text, conn);
                conn.Open();
                cmd.ExecuteNonQuery();
                ds.Clear();
                daPartic.Fill(ds);
                conn.Close();
                showData();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }



        private void showData()
        {
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
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            string title = Configuration.ConfigurationManager.AppSettings["title"];
            connectionString = Configuration.ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
            numeFiu = Configuration.ConfigurationManager.AppSettings["fiu"];
            numeParinte = Configuration.ConfigurationManager.AppSettings["parinte"];
            selectFiu = Configuration.ConfigurationManager.AppSettings["selectFiu"];
            selectParinte = Configuration.ConfigurationManager.AppSettings["selectParinte"];
            idParinte = Configuration.ConfigurationManager.AppSettings["idParinte"];
            idFiu = Configuration.ConfigurationManager.AppSettings["idFiu"];
            List<string> eticheteFiu= new List<string>(Configuration.ConfigurationManager.AppSettings["eticheteFiu"].Split(','));
            coloaneFiu= new List<string>(Configuration.ConfigurationManager.AppSettings["numeColoaneFiu"].Split(','));

            this.Text = title;

            conn = new SqlConnection(connectionString);

            showData();
            
            // generare controale in panel
            Label label;
            TextBox textBox;
            int x = 0;
            int y = 10;

            foreach (string column in eticheteFiu)
            {
                label = new Label();
                label.Text = column;
                label.Location = new Point(x, y);
                panelTextBoxes.Controls.Add(label);

                y += 30;
            }

            x = 100;
            y = 10;
            foreach (string column in coloaneFiu)
            {
                textBox = new TextBox();
                textBox.Name = column;
                
                textBox.Location = new Point(x, y);
                panelTextBoxes.Controls.Add(textBox);
                y += 30;
            }
          
                
        }

        private void buttonSave_Click(object sender, EventArgs e)
        {
            List<string> ColumnNamesInsertParameters
                = new List<string>(Configuration.ConfigurationManager
                .AppSettings["ColumnNamesInsertParameters"].Split(','));
            try
            {
                string sql = "INSERT INTO " +
                    numeFiu + " (" + coloaneFiu + ") VALUES(" + ColumnNamesInsertParameters + ")";
                conn.Open();
                using (SqlCommand cmd = new SqlCommand(sql, conn))
                {
                    foreach (string column in coloaneFiu)
                    {
                        TextBox textBox = (TextBox)panelTextBoxes.Controls[column];
                        cmd.Parameters.AddWithValue("@" + column, textBox.Text);
                    }
                    cmd.ExecuteNonQuery();
                    ds.Clear();
                    daPartic.Fill(ds);
                    conn.Close();
                    MessageBox.Show("Row inserted !! ");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroareeeeeeeee! ");
                MessageBox.Show(ex.Message);
            }
        }
        
    }
}
