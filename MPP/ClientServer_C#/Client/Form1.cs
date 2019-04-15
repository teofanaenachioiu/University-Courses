using Client;
using Concurs.model;
using Services;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApp1
{
    public partial class Form1 : Form 
    {
        ClientCtrl controller;
        BindingSource bsStatistici;
        BindingSource bsInscrieri;
        User user;
        public Form1(ClientCtrl ctrl)
        {
            InitializeComponent();
            controller = ctrl; 
            errorLabel.Visible = false;
            panelOperator.Visible = false;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
           
        }

        private void buttonDeconectare_Click(object sender, EventArgs e)
        {
            panelOperator.Visible = false;
            panelLogin.Visible = true;
            controller.Logout();
        }

        private void buttonConectare_Click(object sender, EventArgs e)
        {
            string username = "";
            string password = "";
            errorLabel.Visible = false;
            
            if (textBoxUsername.Text != null)
                username = textBoxUsername.Text.Trim();
            if (textBoxPassword.Text != null)
                password = textBoxPassword.Text;

            try
            {
                controller.Login(username, password);
                this.user = controller.Cauta(username);
                PopulateGridStatistici();
                PopulateComboProbe();
                PopulateComboCategorii();
                PopulateGridInscrieri();
                InitProbe();
                InitVarsta();
                HandleOperatorWindow();
            }
            catch(MyAppException ex)
            {
                errorLabel.Text = ex.Message;
                errorLabel.Visible = true;
            }
           
            textBoxPassword.Clear();
            textBoxUsername.Text=null;
            textBoxUsername.Focus();
        }

        private void HandleOperatorWindow()
        {
            panelLogin.Visible = false;
            panelOperator.Visible = true;
        }
        
        private void buttonStatistici_Click(object sender, EventArgs e)
        {
            panelStatistici.Visible = true;
            panelInscrieri.Visible = false;
        }

        private void buttonInscrieri_Click(object sender, EventArgs e)
        {
            panelStatistici.Visible = false;
            panelInscrieri.Visible = true;
        }
        
        private void PopulateGridStatistici()
        {
            bsStatistici = new BindingSource();

            DataTable ProbeTable = new DataTable("Probe");

            DataColumn c0 = new DataColumn("Denumire");
            DataColumn c1 = new DataColumn("Categorie");
            DataColumn c2 = new DataColumn("Nr Participanti");

            ProbeTable.Columns.Add(c0);
            ProbeTable.Columns.Add(c1);
            ProbeTable.Columns.Add(c2);

            /*foreach (Proba proba in serviceOperator.ListaProbe())
            {
                DataRow row;
                row = ProbeTable.NewRow();
                row["Denumire"] = proba.Denumire;
                row["Categorie"] = proba.Categorie;
                row["Nr Participanti"] = serviceOperator.NrParticipantiProba(proba);
                ProbeTable.Rows.Add(row);
            }*/

            bsStatistici.DataSource = ProbeTable;
            dataGridViewStatistici.DataSource = bsStatistici;
        }

        private void PopulateGridInscrieri()
        {
            bsInscrieri = new BindingSource();

            DataTable ParticipantiTable = new DataTable("Participanti");

            DataColumn c0 = new DataColumn("Nume");
            DataColumn c1 = new DataColumn("Varsta");
            
            ParticipantiTable.Columns.Add(c0);
            ParticipantiTable.Columns.Add(c1);
            
            foreach (Participant participant in controller.ListaParticipanti())
            {
                DataRow row;
                row = ParticipantiTable.NewRow();
                row["Nume"] = participant.Nume;
                row["Varsta"] = participant.Varsta;
                ParticipantiTable.Rows.Add(row);
            }

            bsInscrieri.DataSource = ParticipantiTable;
            dataGridView2.DataSource = bsInscrieri;
        }

        private void PopulateComboProbe()
        {
            comboBoxProba.DataSource = controller.ListaProbeNume().ToList();
        }

        private void PopulateComboCategorii()
        {
           comboBoxCategorie.DataSource = controller.ListaCategorii().ToList();
        }

        private void buttonReset_Click(object sender, EventArgs e)
        {
            PopulateGridInscrieri();
        }

        private void buttonCauta_Click(object sender, EventArgs e)
        {
            Filter();
        }

        private void Filter()
        {
            bsInscrieri = new BindingSource();

            DataTable ParticipantiTable = new DataTable("Participanti");

            DataColumn c0 = new DataColumn("Nume");
            DataColumn c1 = new DataColumn("Varsta");

            ParticipantiTable.Columns.Add(c0);
            ParticipantiTable.Columns.Add(c1);

            string proba = comboBoxProba.Items[comboBoxProba.SelectedIndex].ToString();
            string categorie = comboBoxCategorie.Items[comboBoxCategorie.SelectedIndex].ToString();
            foreach (Participant participant in controller.FiltreazaParticipantiKeyword(proba,categorie))
            {
                DataRow row;
                row = ParticipantiTable.NewRow();
                row["Nume"] = participant.Nume;
                row["Varsta"] = participant.Varsta;
                ParticipantiTable.Rows.Add(row);
            }

            bsInscrieri.DataSource = ParticipantiTable;
            dataGridView2.DataSource = bsInscrieri;
        }

        private void InitVarsta()
        {
            List<int> lista= new List<int>();
            for (int i = 6; i <= 15; i++)
            {
                lista.Add(i);
            }
            listBoxVarsta.DataSource = lista;
        }

        private void InitProbe()
        {
            foreach (Proba proba in controller.ListaProbe())
            {
                this.checkedListBoxProbe.Items.Add(proba, false);
            }
        }

        private List<Proba> ProbeSelectate()
        {
            List<Proba> lista = new List<Proba>();
            foreach (var item in checkedListBoxProbe.CheckedItems)
            {
                var proba = (Proba)item;
                lista.Add(proba);
            }
            return lista;
        }

        private void buttonAdd_Click(object sender, EventArgs e)
        {
            try
            {
                string nume = textBoxNume.Text;
                int varsta = int.Parse(listBoxVarsta.Text);

                controller.InscriereParticipant(nume, varsta, ProbeSelectate(), user.Id);
                PopulateGridStatistici();
                PopulateGridInscrieri();
                MessageBox.Show("Participantul a fost inscris!");
            } catch(MyAppException ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void dataGridView2_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            var rowsCount = dataGridView2.SelectedRows.Count;
            if (rowsCount == 0 || rowsCount > 1) return;

            var row = dataGridView2.SelectedRows[0];
            if (row == null) return;
            textBoxNume.Text = dataGridView2.Rows[dataGridView2.SelectedRows[0].Index].Cells["Nume"].Value.ToString();
            listBoxVarsta.Text = dataGridView2.Rows[dataGridView2.SelectedRows[0].Index].Cells["Varsta"].Value.ToString();
            //textBoxNume.Text = "haide";
        }

        private void dataGridView2_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            var rowsCount = dataGridView2.SelectedRows.Count;
            if (rowsCount == 0 || rowsCount > 1) return;

            var row = dataGridView2.SelectedRows[0];
            if (row == null) return;
            textBoxNume.Text = dataGridView2.Rows[dataGridView2.SelectedRows[0].Index].Cells["Nume"].Value.ToString();
            listBoxVarsta.Text = dataGridView2.Rows[dataGridView2.SelectedRows[0].Index].Cells["Varsta"].Value.ToString();
            //textBoxNume.Text = "haide";
        }

        private void dataGridView2_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            var rowsCount = dataGridView2.SelectedRows.Count;
            if (rowsCount == 0 || rowsCount > 1) return;

            var row = dataGridView2.SelectedRows[0];
            if (row == null) return;
            textBoxNume.Text = dataGridView2.Rows[dataGridView2.SelectedRows[0].Index].Cells["Nume"].Value.ToString();
            listBoxVarsta.Text = dataGridView2.Rows[dataGridView2.SelectedRows[0].Index].Cells["Varsta"].Value.ToString();
            //textBoxNume.Text = "haide";
        }

        private void comboBoxProba_SelectedIndexChanged(object sender, EventArgs e)
        {
            Filter();
        }

        private void comboBoxCategorie_SelectedIndexChanged(object sender, EventArgs e)
        {
            Filter();
        }

        private void Form1_Close(object sender, FormClosedEventArgs e)
        {
            Console.WriteLine("Window closing " + e.CloseReason);
            if (e.CloseReason == CloseReason.UserClosing)
            {
                if (controller.GetUser() != null)
                {
                    controller.Logout();
                }
                Application.Exit();
            }
        }
    }
}
