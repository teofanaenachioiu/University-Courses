namespace ChristmasGift
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
            this.dgvParinte = new System.Windows.Forms.DataGridView();
            this.dgvFiu = new System.Windows.Forms.DataGridView();
            this.buttonUpdate = new System.Windows.Forms.Button();
            this.labelParinte = new System.Windows.Forms.Label();
            this.labelFiu = new System.Windows.Forms.Label();
            this.labelTitlu = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dgvParinte)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvFiu)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvParinte
            // 
            this.dgvParinte.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvParinte.Location = new System.Drawing.Point(12, 54);
            this.dgvParinte.Name = "dgvParinte";
            this.dgvParinte.Size = new System.Drawing.Size(517, 142);
            this.dgvParinte.TabIndex = 0;
            // 
            // dgvFiu
            // 
            this.dgvFiu.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvFiu.Location = new System.Drawing.Point(12, 228);
            this.dgvFiu.Name = "dgvFiu";
            this.dgvFiu.Size = new System.Drawing.Size(517, 147);
            this.dgvFiu.TabIndex = 1;
            // 
            // buttonUpdate
            // 
            this.buttonUpdate.Location = new System.Drawing.Point(239, 381);
            this.buttonUpdate.Name = "buttonUpdate";
            this.buttonUpdate.Size = new System.Drawing.Size(77, 38);
            this.buttonUpdate.TabIndex = 2;
            this.buttonUpdate.Text = "Save";
            this.buttonUpdate.UseVisualStyleBackColor = true;
            this.buttonUpdate.Click += new System.EventHandler(this.buttonUpdate_Click);
            // 
            // labelParinte
            // 
            this.labelParinte.AutoSize = true;
            this.labelParinte.Location = new System.Drawing.Point(12, 38);
            this.labelParinte.Name = "labelParinte";
            this.labelParinte.Size = new System.Drawing.Size(35, 13);
            this.labelParinte.TabIndex = 3;
            this.labelParinte.Text = "label1";
            // 
            // labelFiu
            // 
            this.labelFiu.AutoSize = true;
            this.labelFiu.Location = new System.Drawing.Point(12, 212);
            this.labelFiu.Name = "labelFiu";
            this.labelFiu.Size = new System.Drawing.Size(35, 13);
            this.labelFiu.TabIndex = 4;
            this.labelFiu.Text = "label2";
            // 
            // labelTitlu
            // 
            this.labelTitlu.AutoSize = true;
            this.labelTitlu.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelTitlu.Location = new System.Drawing.Point(400, 9);
            this.labelTitlu.Name = "labelTitlu";
            this.labelTitlu.Size = new System.Drawing.Size(70, 26);
            this.labelTitlu.TabIndex = 5;
            this.labelTitlu.Text = "label3";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(541, 431);
            this.Controls.Add(this.labelTitlu);
            this.Controls.Add(this.labelFiu);
            this.Controls.Add(this.labelParinte);
            this.Controls.Add(this.buttonUpdate);
            this.Controls.Add(this.dgvFiu);
            this.Controls.Add(this.dgvParinte);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvParinte)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvFiu)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvParinte;
        private System.Windows.Forms.DataGridView dgvFiu;
        private System.Windows.Forms.Button buttonUpdate;
        private System.Windows.Forms.Label labelParinte;
        private System.Windows.Forms.Label labelFiu;
        private System.Windows.Forms.Label labelTitlu;
    }
}

