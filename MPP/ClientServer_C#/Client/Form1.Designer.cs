namespace WindowsFormsApp1
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.textBoxUsername = new System.Windows.Forms.TextBox();
            this.textBoxPassword = new System.Windows.Forms.TextBox();
            this.labelUsername = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.panelLogin = new System.Windows.Forms.Panel();
            this.errorLabel = new System.Windows.Forms.Label();
            this.buttonConectare = new System.Windows.Forms.Button();
            this.panelOperator = new System.Windows.Forms.Panel();
            this.panelInscrieri = new System.Windows.Forms.Panel();
            this.buttonReset = new System.Windows.Forms.Button();
            this.buttonCauta = new System.Windows.Forms.Button();
            this.label8 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.comboBoxCategorie = new System.Windows.Forms.ComboBox();
            this.comboBoxProba = new System.Windows.Forms.ComboBox();
            this.buttonAdd = new System.Windows.Forms.Button();
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.checkedListBoxProbe = new System.Windows.Forms.CheckedListBox();
            this.label4 = new System.Windows.Forms.Label();
            this.listBoxVarsta = new System.Windows.Forms.ListBox();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.textBoxNume = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.panelStatistici = new System.Windows.Forms.Panel();
            this.dataGridViewStatistici = new System.Windows.Forms.DataGridView();
            this.label3 = new System.Windows.Forms.Label();
            this.panelDetails = new System.Windows.Forms.Panel();
            this.buttonDeconectare = new System.Windows.Forms.Button();
            this.buttonStatistici = new System.Windows.Forms.Button();
            this.buttonInscrieri = new System.Windows.Forms.Button();
            this.panelTitle = new System.Windows.Forms.Panel();
            this.labelOperator = new System.Windows.Forms.Label();
            this.panelLogin.SuspendLayout();
            this.panelOperator.SuspendLayout();
            this.panelInscrieri.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.panelStatistici.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewStatistici)).BeginInit();
            this.panelDetails.SuspendLayout();
            this.panelTitle.SuspendLayout();
            this.SuspendLayout();
            // 
            // textBoxUsername
            // 
            this.textBoxUsername.Location = new System.Drawing.Point(280, 135);
            this.textBoxUsername.Name = "textBoxUsername";
            this.textBoxUsername.Size = new System.Drawing.Size(100, 20);
            this.textBoxUsername.TabIndex = 0;
            // 
            // textBoxPassword
            // 
            this.textBoxPassword.Location = new System.Drawing.Point(280, 163);
            this.textBoxPassword.Name = "textBoxPassword";
            this.textBoxPassword.PasswordChar = '*';
            this.textBoxPassword.Size = new System.Drawing.Size(100, 20);
            this.textBoxPassword.TabIndex = 1;
            // 
            // labelUsername
            // 
            this.labelUsername.AutoSize = true;
            this.labelUsername.BackColor = System.Drawing.Color.Transparent;
            this.labelUsername.Location = new System.Drawing.Point(219, 138);
            this.labelUsername.Name = "labelUsername";
            this.labelUsername.Size = new System.Drawing.Size(55, 13);
            this.labelUsername.TabIndex = 2;
            this.labelUsername.Text = "Username";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.BackColor = System.Drawing.Color.Transparent;
            this.label1.Location = new System.Drawing.Point(219, 166);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(53, 13);
            this.label1.TabIndex = 3;
            this.label1.Text = "Password";
            // 
            // panelLogin
            // 
            this.panelLogin.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("panelLogin.BackgroundImage")));
            this.panelLogin.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.panelLogin.Controls.Add(this.errorLabel);
            this.panelLogin.Controls.Add(this.buttonConectare);
            this.panelLogin.Controls.Add(this.textBoxPassword);
            this.panelLogin.Controls.Add(this.textBoxUsername);
            this.panelLogin.Controls.Add(this.label1);
            this.panelLogin.Controls.Add(this.labelUsername);
            this.panelLogin.Location = new System.Drawing.Point(1, 0);
            this.panelLogin.Name = "panelLogin";
            this.panelLogin.Size = new System.Drawing.Size(600, 400);
            this.panelLogin.TabIndex = 4;
            // 
            // errorLabel
            // 
            this.errorLabel.AutoSize = true;
            this.errorLabel.BackColor = System.Drawing.Color.Transparent;
            this.errorLabel.Location = new System.Drawing.Point(311, 183);
            this.errorLabel.Name = "errorLabel";
            this.errorLabel.Size = new System.Drawing.Size(72, 13);
            this.errorLabel.TabIndex = 5;
            this.errorLabel.Text = "Date invalide!";
            // 
            // buttonConectare
            // 
            this.buttonConectare.Location = new System.Drawing.Point(266, 199);
            this.buttonConectare.Name = "buttonConectare";
            this.buttonConectare.Size = new System.Drawing.Size(75, 23);
            this.buttonConectare.TabIndex = 4;
            this.buttonConectare.Text = "Conectare";
            this.buttonConectare.UseVisualStyleBackColor = true;
            this.buttonConectare.Click += new System.EventHandler(this.buttonConectare_Click);
            // 
            // panelOperator
            // 
            this.panelOperator.Controls.Add(this.panelInscrieri);
            this.panelOperator.Controls.Add(this.panelStatistici);
            this.panelOperator.Controls.Add(this.panelDetails);
            this.panelOperator.Controls.Add(this.panelTitle);
            this.panelOperator.Location = new System.Drawing.Point(0, 0);
            this.panelOperator.Name = "panelOperator";
            this.panelOperator.Size = new System.Drawing.Size(600, 400);
            this.panelOperator.TabIndex = 5;
            // 
            // panelInscrieri
            // 
            this.panelInscrieri.Controls.Add(this.buttonReset);
            this.panelInscrieri.Controls.Add(this.buttonCauta);
            this.panelInscrieri.Controls.Add(this.label8);
            this.panelInscrieri.Controls.Add(this.label7);
            this.panelInscrieri.Controls.Add(this.comboBoxCategorie);
            this.panelInscrieri.Controls.Add(this.comboBoxProba);
            this.panelInscrieri.Controls.Add(this.buttonAdd);
            this.panelInscrieri.Controls.Add(this.label6);
            this.panelInscrieri.Controls.Add(this.label5);
            this.panelInscrieri.Controls.Add(this.checkedListBoxProbe);
            this.panelInscrieri.Controls.Add(this.label4);
            this.panelInscrieri.Controls.Add(this.listBoxVarsta);
            this.panelInscrieri.Controls.Add(this.dataGridView2);
            this.panelInscrieri.Controls.Add(this.textBoxNume);
            this.panelInscrieri.Controls.Add(this.label2);
            this.panelInscrieri.Location = new System.Drawing.Point(80, 60);
            this.panelInscrieri.Name = "panelInscrieri";
            this.panelInscrieri.Size = new System.Drawing.Size(520, 313);
            this.panelInscrieri.TabIndex = 7;
            // 
            // buttonReset
            // 
            this.buttonReset.Location = new System.Drawing.Point(201, 255);
            this.buttonReset.Name = "buttonReset";
            this.buttonReset.Size = new System.Drawing.Size(75, 23);
            this.buttonReset.TabIndex = 14;
            this.buttonReset.Text = "Reset";
            this.buttonReset.UseVisualStyleBackColor = true;
            this.buttonReset.Click += new System.EventHandler(this.buttonReset_Click);
            // 
            // buttonCauta
            // 
            this.buttonCauta.Location = new System.Drawing.Point(201, 223);
            this.buttonCauta.Name = "buttonCauta";
            this.buttonCauta.Size = new System.Drawing.Size(75, 23);
            this.buttonCauta.TabIndex = 13;
            this.buttonCauta.Text = "Cauta";
            this.buttonCauta.UseVisualStyleBackColor = true;
            this.buttonCauta.Click += new System.EventHandler(this.buttonCauta_Click);
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(25, 258);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(52, 13);
            this.label8.TabIndex = 12;
            this.label8.Text = "Categorie";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(25, 231);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(35, 13);
            this.label7.TabIndex = 11;
            this.label7.Text = "Proba";
            // 
            // comboBoxCategorie
            // 
            this.comboBoxCategorie.FormattingEnabled = true;
            this.comboBoxCategorie.Location = new System.Drawing.Point(77, 255);
            this.comboBoxCategorie.Name = "comboBoxCategorie";
            this.comboBoxCategorie.Size = new System.Drawing.Size(118, 21);
            this.comboBoxCategorie.TabIndex = 10;
            this.comboBoxCategorie.SelectedIndexChanged += new System.EventHandler(this.comboBoxCategorie_SelectedIndexChanged);
            // 
            // comboBoxProba
            // 
            this.comboBoxProba.FormattingEnabled = true;
            this.comboBoxProba.Location = new System.Drawing.Point(77, 226);
            this.comboBoxProba.Name = "comboBoxProba";
            this.comboBoxProba.Size = new System.Drawing.Size(118, 21);
            this.comboBoxProba.TabIndex = 9;
            this.comboBoxProba.SelectedIndexChanged += new System.EventHandler(this.comboBoxProba_SelectedIndexChanged);
            // 
            // buttonAdd
            // 
            this.buttonAdd.Location = new System.Drawing.Point(405, 223);
            this.buttonAdd.Name = "buttonAdd";
            this.buttonAdd.Size = new System.Drawing.Size(75, 23);
            this.buttonAdd.TabIndex = 8;
            this.buttonAdd.Text = "Inscrieri";
            this.buttonAdd.UseVisualStyleBackColor = true;
            this.buttonAdd.Click += new System.EventHandler(this.buttonAdd_Click);
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(281, 158);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(35, 13);
            this.label6.TabIndex = 7;
            this.label6.Text = "Probe";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(281, 86);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(37, 13);
            this.label5.TabIndex = 6;
            this.label5.Text = "Varsta";
            // 
            // checkedListBoxProbe
            // 
            this.checkedListBoxProbe.FormattingEnabled = true;
            this.checkedListBoxProbe.Location = new System.Drawing.Point(323, 152);
            this.checkedListBoxProbe.Name = "checkedListBoxProbe";
            this.checkedListBoxProbe.Size = new System.Drawing.Size(169, 49);
            this.checkedListBoxProbe.TabIndex = 5;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(281, 58);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(35, 13);
            this.label4.TabIndex = 4;
            this.label4.Text = "Nume";
            // 
            // listBoxVarsta
            // 
            this.listBoxVarsta.FormattingEnabled = true;
            this.listBoxVarsta.Location = new System.Drawing.Point(323, 82);
            this.listBoxVarsta.Name = "listBoxVarsta";
            this.listBoxVarsta.Size = new System.Drawing.Size(169, 56);
            this.listBoxVarsta.TabIndex = 3;
            // 
            // dataGridView2
            // 
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Location = new System.Drawing.Point(25, 25);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.Size = new System.Drawing.Size(250, 193);
            this.dataGridView2.TabIndex = 2;
            this.dataGridView2.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView2_CellClick);
            this.dataGridView2.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView2_CellContentClick);
            this.dataGridView2.CellDoubleClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView2_CellDoubleClick);
            // 
            // textBoxNume
            // 
            this.textBoxNume.Location = new System.Drawing.Point(323, 52);
            this.textBoxNume.Name = "textBoxNume";
            this.textBoxNume.Size = new System.Drawing.Size(169, 20);
            this.textBoxNume.TabIndex = 1;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(420, 20);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(57, 17);
            this.label2.TabIndex = 0;
            this.label2.Text = "Inscrieri";
            // 
            // panelStatistici
            // 
            this.panelStatistici.Controls.Add(this.dataGridViewStatistici);
            this.panelStatistici.Controls.Add(this.label3);
            this.panelStatistici.Location = new System.Drawing.Point(80, 60);
            this.panelStatistici.Name = "panelStatistici";
            this.panelStatistici.Size = new System.Drawing.Size(520, 340);
            this.panelStatistici.TabIndex = 6;
            // 
            // dataGridViewStatistici
            // 
            this.dataGridViewStatistici.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewStatistici.Location = new System.Drawing.Point(25, 58);
            this.dataGridViewStatistici.Name = "dataGridViewStatistici";
            this.dataGridViewStatistici.Size = new System.Drawing.Size(452, 170);
            this.dataGridViewStatistici.TabIndex = 2;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(420, 20);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(60, 17);
            this.label3.TabIndex = 1;
            this.label3.Text = "Statistici";
            // 
            // panelDetails
            // 
            this.panelDetails.BackColor = System.Drawing.Color.PaleGreen;
            this.panelDetails.Controls.Add(this.buttonDeconectare);
            this.panelDetails.Controls.Add(this.buttonStatistici);
            this.panelDetails.Controls.Add(this.buttonInscrieri);
            this.panelDetails.Location = new System.Drawing.Point(0, 60);
            this.panelDetails.Name = "panelDetails";
            this.panelDetails.Size = new System.Drawing.Size(80, 340);
            this.panelDetails.TabIndex = 5;
            // 
            // buttonDeconectare
            // 
            this.buttonDeconectare.Location = new System.Drawing.Point(0, 116);
            this.buttonDeconectare.Name = "buttonDeconectare";
            this.buttonDeconectare.Size = new System.Drawing.Size(80, 60);
            this.buttonDeconectare.TabIndex = 1;
            this.buttonDeconectare.Text = "Deconectare";
            this.buttonDeconectare.UseVisualStyleBackColor = true;
            this.buttonDeconectare.Click += new System.EventHandler(this.buttonDeconectare_Click);
            // 
            // buttonStatistici
            // 
            this.buttonStatistici.Location = new System.Drawing.Point(0, 0);
            this.buttonStatistici.Name = "buttonStatistici";
            this.buttonStatistici.Size = new System.Drawing.Size(80, 60);
            this.buttonStatistici.TabIndex = 2;
            this.buttonStatistici.Text = "Statistici";
            this.buttonStatistici.UseVisualStyleBackColor = true;
            this.buttonStatistici.Click += new System.EventHandler(this.buttonStatistici_Click);
            // 
            // buttonInscrieri
            // 
            this.buttonInscrieri.Location = new System.Drawing.Point(0, 58);
            this.buttonInscrieri.Name = "buttonInscrieri";
            this.buttonInscrieri.Size = new System.Drawing.Size(80, 60);
            this.buttonInscrieri.TabIndex = 3;
            this.buttonInscrieri.Text = "Inscrieri";
            this.buttonInscrieri.UseVisualStyleBackColor = true;
            this.buttonInscrieri.Click += new System.EventHandler(this.buttonInscrieri_Click);
            // 
            // panelTitle
            // 
            this.panelTitle.BackColor = System.Drawing.Color.MediumSeaGreen;
            this.panelTitle.Controls.Add(this.labelOperator);
            this.panelTitle.Location = new System.Drawing.Point(0, 0);
            this.panelTitle.Name = "panelTitle";
            this.panelTitle.Size = new System.Drawing.Size(600, 60);
            this.panelTitle.TabIndex = 4;
            // 
            // labelOperator
            // 
            this.labelOperator.AutoSize = true;
            this.labelOperator.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelOperator.Location = new System.Drawing.Point(476, 19);
            this.labelOperator.Name = "labelOperator";
            this.labelOperator.Size = new System.Drawing.Size(80, 20);
            this.labelOperator.TabIndex = 0;
            this.labelOperator.Text = "Operator";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(584, 361);
            this.Controls.Add(this.panelOperator);
            this.Controls.Add(this.panelLogin);
            this.Name = "Form1";
            this.Text = "Form1";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Form1_Close);
            this.Load += new System.EventHandler(this.Form1_Load);
            this.panelLogin.ResumeLayout(false);
            this.panelLogin.PerformLayout();
            this.panelOperator.ResumeLayout(false);
            this.panelInscrieri.ResumeLayout(false);
            this.panelInscrieri.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.panelStatistici.ResumeLayout(false);
            this.panelStatistici.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewStatistici)).EndInit();
            this.panelDetails.ResumeLayout(false);
            this.panelTitle.ResumeLayout(false);
            this.panelTitle.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TextBox textBoxUsername;
        private System.Windows.Forms.TextBox textBoxPassword;
        private System.Windows.Forms.Label labelUsername;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Panel panelLogin;
        private System.Windows.Forms.Button buttonConectare;
        private System.Windows.Forms.Panel panelOperator;
        private System.Windows.Forms.Button buttonDeconectare;
        private System.Windows.Forms.Label labelOperator;
        private System.Windows.Forms.Label errorLabel;
        private System.Windows.Forms.Panel panelDetails;
        private System.Windows.Forms.Button buttonStatistici;
        private System.Windows.Forms.Button buttonInscrieri;
        private System.Windows.Forms.Panel panelTitle;
        private System.Windows.Forms.Panel panelStatistici;
        private System.Windows.Forms.Panel panelInscrieri;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.CheckedListBox checkedListBoxProbe;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ListBox listBoxVarsta;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.TextBox textBoxNume;
        private System.Windows.Forms.DataGridView dataGridViewStatistici;
        private System.Windows.Forms.Button buttonAdd;
        private System.Windows.Forms.Button buttonReset;
        private System.Windows.Forms.Button buttonCauta;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.ComboBox comboBoxCategorie;
        private System.Windows.Forms.ComboBox comboBoxProba;
    }
}

