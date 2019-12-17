using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CinemaCity
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            SetColorScheme();
            Microsoft.Win32.SystemEvents.UserPreferenceChanged
                += new Microsoft.Win32.UserPreferenceChangedEventHandler(
                    UserPreferenceChanged);

            InitializaList();
        }

        private void InitializaList()
        {
            filmList.Items.Add("Singur Acasa");
            filmList.Items.Add("Frozen II");
            filmList.Items.Add("Ingerii lui Charlie");
            filmList.Items.Add("Jumanji");
            filmList.Items.Add("Marea provocare");
            filmList.Items.Add("Maestrul minciunilor");
        }


        public void UserPreferenceChanged(object sender, Microsoft.Win32.UserPreferenceChangedEventArgs e)
        {
            SetColorScheme();
        }

        private void SetColorScheme()
        {
            if (SystemInformation.HighContrast)
            {
                SetFontColor(SystemColors.WindowText);
                SetBackColor(SystemColors.Window);
            }
            else
            {
                //cinemaLabel.BackColor = Color.Blue;
                //cinemaLabel.ForeColor = Color.Yellow;
            }
        }


        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void SetSize(float currentSize)
        {
            Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);

            cinemaLabel.Font = new Font(Font.Name, currentSize, cinemaLabel.Font.Style, Font.Unit);
            filmLabel.Font = new Font(Font.Name, currentSize, filmLabel.Font.Style, Font.Unit);
            filmList.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            locuriLabel.Font = new Font(Font.Name, currentSize, locuriLabel.Font.Style, Font.Unit);
            locuriTextBox.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            actiuneLabel.Font = new Font(Font.Name, currentSize, actiuneLabel.Font.Style, Font.Unit);
            rezervaButton.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            comandaButton.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            clientLabel.Font = new Font(Font.Name, currentSize, clientLabel.Font.Style, Font.Unit);
            numeLabel.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            numeTextBox.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            prenumeLabel.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            prenumeTextBox.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);


            fileToolStripMenuItem.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            settingsToolStripMenuItem.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            smallerFontSizeToolStripMenuItem.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            biggerFontSizeToolStripMenuItem.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            fontColorToolStripMenuItem.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
            backgroundColorToolStripMenuItem.Font = new Font(Font.Name, currentSize, Font.Style, Font.Unit);
        }

        private void BiggerFontSizeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            float currentSize = Font.Size;
            currentSize += 2.0F;

            SetSize(currentSize);
        }



        private void SmallerFontSizeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            float currentSize = Font.SizeInPoints;
            currentSize -= 1;

            SetSize(currentSize);

        }

        private void BackgroundColorToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ColorDialog MyDialog = new ColorDialog();

            MyDialog.AllowFullOpen = false;
            MyDialog.ShowHelp = true;
            MyDialog.Color = numeTextBox.ForeColor;

            if (MyDialog.ShowDialog() == DialogResult.OK)
            {
                SetBackColor(MyDialog.Color);
            }
        }

        private void FontColorToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ColorDialog MyDialog = new ColorDialog();
            MyDialog.AllowFullOpen = false;
            MyDialog.ShowHelp = true;
            MyDialog.Color = numeTextBox.ForeColor;

            if (MyDialog.ShowDialog() == DialogResult.OK)
            {
                SetFontColor(MyDialog.Color);
            }
        }

        private void SetBackColor(Color color)
        { 
            BackColor = color;
            filmList.BackColor = color;
            locuriTextBox.BackColor = color;
            numeTextBox.BackColor = color;
            prenumeTextBox.BackColor = color;
            nextButton.BackColor = color;
            comandaButton.BackColor = color;
            rezervaButton.BackColor = color;
            theMainMenu.BackColor = color;
            fileToolStripMenuItem.BackColor = color;
            settingsToolStripMenuItem.BackColor = color;
        }

        private void SetFontColor(Color color)
        {
            filmLabel.ForeColor = color;
            filmList.ForeColor = color;
            locuriLabel.ForeColor = color;
            locuriTextBox.ForeColor = color;
            actiuneLabel.ForeColor = color;
            rezervaButton.ForeColor = color;
            comandaButton.ForeColor = color;
            clientLabel.ForeColor = color;
            numeLabel.ForeColor = color;
            numeTextBox.ForeColor = color;
            prenumeLabel.ForeColor = color;
            prenumeTextBox.ForeColor = color;
            nextButton.ForeColor = color;
            rezervaButton.ForeColor = color;
            comandaButton.ForeColor = color;
            fileToolStripMenuItem.ForeColor = color;
            settingsToolStripMenuItem.ForeColor = color;
            closeToolStripMenuItem.ForeColor = color;
            smallerFontSizeToolStripMenuItem.ForeColor = color;
            biggerFontSizeToolStripMenuItem.ForeColor = color;
            fontColorToolStripMenuItem.ForeColor = color;
            backgroundColorToolStripMenuItem.ForeColor = color;
            cinemaLabel.ForeColor = color;
        }

        private void CloseToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Close();
        }

        protected override bool ProcessCmdKey(ref Message msg, Keys keyData)
        {
            if (keyData == Keys.Escape)
            {
                Close();
                return true;
            }
            return base.ProcessCmdKey(ref msg, keyData);
        }

        private void theMainMenu_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {

        }
    }
}
