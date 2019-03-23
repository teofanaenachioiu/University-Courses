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
            this.panelStatistici = new System.Windows.Forms.Panel();
            this.panelInscrieri = new System.Windows.Forms.Panel();
            this.label2 = new System.Windows.Forms.Label();
            this.panelDetails = new System.Windows.Forms.Panel();
            this.buttonDeconectare = new System.Windows.Forms.Button();
            this.buttonStatistici = new System.Windows.Forms.Button();
            this.buttonInscrieri = new System.Windows.Forms.Button();
            this.panelTitle = new System.Windows.Forms.Panel();
            this.labelOperator = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.listBox1 = new System.Windows.Forms.ListBox();
            this.label4 = new System.Windows.Forms.Label();
            this.checkedListBox1 = new System.Windows.Forms.CheckedListBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.buttonAdd = new System.Windows.Forms.Button();
            this.panelLogin.SuspendLayout();
            this.panelOperator.SuspendLayout();
            this.panelStatistici.SuspendLayout();
            this.panelInscrieri.SuspendLayout();
            this.panelDetails.SuspendLayout();
            this.panelTitle.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.SuspendLayout();
            // 
            // textBoxUsername
            // 
            this.textBoxUsername.Location = new System.Drawing.Point(247, 135);
            this.textBoxUsername.Name = "textBoxUsername";
            this.textBoxUsername.Size = new System.Drawing.Size(100, 20);
            this.textBoxUsername.TabIndex = 0;
            // 
            // textBoxPassword
            // 
            this.textBoxPassword.Location = new System.Drawing.Point(247, 163);
            this.textBoxPassword.Name = "textBoxPassword";
            this.textBoxPassword.PasswordChar = '*';
            this.textBoxPassword.Size = new System.Drawing.Size(100, 20);
            this.textBoxPassword.TabIndex = 1;
            // 
            // labelUsername
            // 
            this.labelUsername.AutoSize = true;
            this.labelUsername.BackColor = System.Drawing.Color.Transparent;
            this.labelUsername.Location = new System.Drawing.Point(186, 138);
            this.labelUsername.Name = "labelUsername";
            this.labelUsername.Size = new System.Drawing.Size(55, 13);
            this.labelUsername.TabIndex = 2;
            this.labelUsername.Text = "Username";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.BackColor = System.Drawing.Color.Transparent;
            this.label1.Location = new System.Drawing.Point(186, 166);
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
            this.panelLogin.Location = new System.Drawing.Point(0, 0);
            this.panelLogin.Name = "panelLogin";
            this.panelLogin.Size = new System.Drawing.Size(500, 400);
            this.panelLogin.TabIndex = 4;
            // 
            // errorLabel
            // 
            this.errorLabel.AutoSize = true;
            this.errorLabel.BackColor = System.Drawing.Color.Transparent;
            this.errorLabel.Location = new System.Drawing.Point(278, 183);
            this.errorLabel.Name = "errorLabel";
            this.errorLabel.Size = new System.Drawing.Size(72, 13);
            this.errorLabel.TabIndex = 5;
            this.errorLabel.Text = "Date invalide!";
            // 
            // buttonConectare
            // 
            this.buttonConectare.Location = new System.Drawing.Point(233, 199);
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
            this.panelOperator.Size = new System.Drawing.Size(500, 400);
            this.panelOperator.TabIndex = 5;
            // 
            // panelStatistici
            // 
            this.panelStatistici.Controls.Add(this.dataGridView1);
            this.panelStatistici.Controls.Add(this.label3);
            this.panelStatistici.Location = new System.Drawing.Point(80, 60);
            this.panelStatistici.Name = "panelStatistici";
            this.panelStatistici.Size = new System.Drawing.Size(420, 340);
            this.panelStatistici.TabIndex = 6;
            // 
            // panelInscrieri
            // 
            this.panelInscrieri.Controls.Add(this.buttonAdd);
            this.panelInscrieri.Controls.Add(this.label6);
            this.panelInscrieri.Controls.Add(this.label5);
            this.panelInscrieri.Controls.Add(this.checkedListBox1);
            this.panelInscrieri.Controls.Add(this.label4);
            this.panelInscrieri.Controls.Add(this.listBox1);
            this.panelInscrieri.Controls.Add(this.dataGridView2);
            this.panelInscrieri.Controls.Add(this.textBox1);
            this.panelInscrieri.Controls.Add(this.label2);
            this.panelInscrieri.Location = new System.Drawing.Point(80, 60);
            this.panelInscrieri.Name = "panelInscrieri";
            this.panelInscrieri.Size = new System.Drawing.Size(420, 340);
            this.panelInscrieri.TabIndex = 7;
            this.panelInscrieri.Paint += new System.Windows.Forms.PaintEventHandler(this.panelInscrieri_Paint);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(340, 25);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(43, 13);
            this.label2.TabIndex = 0;
            this.label2.Text = "Inscrieri";
            this.label2.Click += new System.EventHandler(this.label2_Click);
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
            this.panelTitle.Size = new System.Drawing.Size(500, 60);
            this.panelTitle.TabIndex = 4;
            // 
            // labelOperator
            // 
            this.labelOperator.AutoSize = true;
            this.labelOperator.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelOperator.Location = new System.Drawing.Point(385, 22);
            this.labelOperator.Name = "labelOperator";
            this.labelOperator.Size = new System.Drawing.Size(80, 20);
            this.labelOperator.TabIndex = 0;
            this.labelOperator.Text = "Operator";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(340, 25);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(46, 13);
            this.label3.TabIndex = 1;
            this.label3.Text = "Statistici";
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(45, 60);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.Size = new System.Drawing.Size(320, 170);
            this.dataGridView1.TabIndex = 2;
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(280, 60);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(100, 20);
            this.textBox1.TabIndex = 1;
            // 
            // dataGridView2
            // 
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Location = new System.Drawing.Point(25, 25);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.Size = new System.Drawing.Size(150, 250);
            this.dataGridView2.TabIndex = 2;
            // 
            // listBox1
            // 
            this.listBox1.FormattingEnabled = true;
            this.listBox1.Location = new System.Drawing.Point(280, 90);
            this.listBox1.Name = "listBox1";
            this.listBox1.Size = new System.Drawing.Size(100, 17);
            this.listBox1.TabIndex = 3;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(224, 66);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(35, 13);
            this.label4.TabIndex = 4;
            this.label4.Text = "Nume";
            // 
            // checkedListBox1
            // 
            this.checkedListBox1.FormattingEnabled = true;
            this.checkedListBox1.Location = new System.Drawing.Point(280, 120);
            this.checkedListBox1.Name = "checkedListBox1";
            this.checkedListBox1.Size = new System.Drawing.Size(100, 19);
            this.checkedListBox1.TabIndex = 5;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(224, 94);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(37, 13);
            this.label5.TabIndex = 6;
            this.label5.Text = "Varsta";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(225, 123);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(35, 13);
            this.label6.TabIndex = 7;
            this.label6.Text = "Probe";
            // 
            // buttonAdd
            // 
            this.buttonAdd.Location = new System.Drawing.Point(305, 153);
            this.buttonAdd.Name = "buttonAdd";
            this.buttonAdd.Size = new System.Drawing.Size(75, 23);
            this.buttonAdd.TabIndex = 8;
            this.buttonAdd.Text = "Inscrieri";
            this.buttonAdd.UseVisualStyleBackColor = true;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(484, 361);
            this.Controls.Add(this.panelOperator);
            this.Controls.Add(this.panelLogin);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.panelLogin.ResumeLayout(false);
            this.panelLogin.PerformLayout();
            this.panelOperator.ResumeLayout(false);
            this.panelStatistici.ResumeLayout(false);
            this.panelStatistici.PerformLayout();
            this.panelInscrieri.ResumeLayout(false);
            this.panelInscrieri.PerformLayout();
            this.panelDetails.ResumeLayout(false);
            this.panelTitle.ResumeLayout(false);
            this.panelTitle.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
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
        private System.Windows.Forms.CheckedListBox checkedListBox1;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ListBox listBox1;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.Button buttonAdd;
    }
}

