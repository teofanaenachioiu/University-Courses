using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SeminarDesigner
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void evenimenteBindingNavigatorSaveItem_Click(object sender, EventArgs e)
        {
            this.Validate();
            this.evenimenteBindingSource.EndEdit();
            this.tableAdapterManager.UpdateAll(this.tabaraDeVaraDataSet);

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            // TODO: This line of code loads data into the 'tabaraDeVaraDataSet.Evenimente' table. You can move, or remove it, as needed.
            this.evenimenteTableAdapter.Fill(this.tabaraDeVaraDataSet.Evenimente);

        }
    }
}
