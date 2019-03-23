using Concurs.model;
using Concurs.repository;
using Concurs.service;
using log4net.Config;
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
        ServiceAdmin serviceAdmin;
        IRepositoryUser userRepository;
        public Form1()
        {
            InitializeComponent();
            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

            IDictionary<String, string> props = new SortedList<String, String>();

            props.Add("ConnectionString", DBUtils.GetConnectionStringByName("concurs"));
            userRepository = new UserRepository(props);
            serviceAdmin = new ServiceAdmin(userRepository);
           
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

            bool valid = serviceAdmin.VerificareParola(username, password);
            if (valid)
            {
                User utilizator = serviceAdmin.Cauta(username);
                HandleOperatorWindow();
            }
            else
            {
                errorLabel.Visible=true;
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

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void panelInscrieri_Paint(object sender, PaintEventArgs e)
        {

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
    }
}
