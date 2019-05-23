namespace MiniFacebook
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
            this.dgvUtilizatori = new System.Windows.Forms.DataGridView();
            this.dgvPostari = new System.Windows.Forms.DataGridView();
            this.btnUpdateBd = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dgvUtilizatori)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvPostari)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvUtilizatori
            // 
            this.dgvUtilizatori.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvUtilizatori.Location = new System.Drawing.Point(12, 60);
            this.dgvUtilizatori.Name = "dgvUtilizatori";
            this.dgvUtilizatori.Size = new System.Drawing.Size(491, 150);
            this.dgvUtilizatori.TabIndex = 0;
            // 
            // dgvPostari
            // 
            this.dgvPostari.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvPostari.Location = new System.Drawing.Point(12, 235);
            this.dgvPostari.Name = "dgvPostari";
            this.dgvPostari.Size = new System.Drawing.Size(491, 150);
            this.dgvPostari.TabIndex = 1;
            // 
            // btnUpdateBd
            // 
            this.btnUpdateBd.Location = new System.Drawing.Point(184, 391);
            this.btnUpdateBd.Name = "btnUpdateBd";
            this.btnUpdateBd.Size = new System.Drawing.Size(149, 42);
            this.btnUpdateBd.TabIndex = 2;
            this.btnUpdateBd.Text = "Update BD";
            this.btnUpdateBd.UseVisualStyleBackColor = true;
            this.btnUpdateBd.Click += new System.EventHandler(this.btnUpdateBd_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Segoe Print", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(19, 38);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(64, 19);
            this.label1.TabIndex = 3;
            this.label1.Text = "Utilizatori";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Segoe Print", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(19, 213);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(48, 19);
            this.label2.TabIndex = 4;
            this.label2.Text = "Postari";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Segoe Print", 18F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(323, 9);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(180, 43);
            this.label3.TabIndex = 5;
            this.label3.Text = "MiniFacebook";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(521, 445);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.btnUpdateBd);
            this.Controls.Add(this.dgvPostari);
            this.Controls.Add(this.dgvUtilizatori);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvUtilizatori)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvPostari)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvUtilizatori;
        private System.Windows.Forms.DataGridView dgvPostari;
        private System.Windows.Forms.Button btnUpdateBd;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
    }
}

