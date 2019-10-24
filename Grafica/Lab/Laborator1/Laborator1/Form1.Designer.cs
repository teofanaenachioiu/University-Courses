using System;

namespace Laborator1
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
            this.buttonCircle = new System.Windows.Forms.Button();
            this.buttonHistogram = new System.Windows.Forms.Button();
            this.buttonCurve = new System.Windows.Forms.Button();
            this.labelx = new System.Windows.Forms.Label();
            this.labely = new System.Windows.Forms.Label();
            this.panel1 = new System.Windows.Forms.Panel();
            this.SuspendLayout();
            // 
            // buttonCircle
            // 
            this.buttonCircle.Location = new System.Drawing.Point(248, 375);
            this.buttonCircle.Name = "buttonCircle";
            this.buttonCircle.Size = new System.Drawing.Size(75, 50);
            this.buttonCircle.TabIndex = 0;
            this.buttonCircle.Text = "Circle";
            this.buttonCircle.UseVisualStyleBackColor = true;
            this.buttonCircle.Click += new System.EventHandler(this.buttonCircle_Click);
            // 
            // buttonHistogram
            // 
            this.buttonHistogram.Location = new System.Drawing.Point(363, 375);
            this.buttonHistogram.Name = "buttonHistogram";
            this.buttonHistogram.Size = new System.Drawing.Size(75, 50);
            this.buttonHistogram.TabIndex = 2;
            this.buttonHistogram.Text = "Histogram";
            this.buttonHistogram.UseVisualStyleBackColor = true;
            // 
            // buttonCurve
            // 
            this.buttonCurve.Location = new System.Drawing.Point(479, 375);
            this.buttonCurve.Name = "buttonCurve";
            this.buttonCurve.Size = new System.Drawing.Size(75, 50);
            this.buttonCurve.TabIndex = 3;
            this.buttonCurve.Text = "Curve";
            this.buttonCurve.UseVisualStyleBackColor = true;
            // 
            // labelx
            // 
            this.labelx.AutoSize = true;
            this.labelx.Location = new System.Drawing.Point(625, 375);
            this.labelx.Name = "labelx";
            this.labelx.Size = new System.Drawing.Size(34, 13);
            this.labelx.TabIndex = 4;
            this.labelx.Text = "labelx";
            // 
            // labely
            // 
            this.labely.AutoSize = true;
            this.labely.Location = new System.Drawing.Point(625, 394);
            this.labely.Name = "labely";
            this.labely.Size = new System.Drawing.Size(34, 13);
            this.labely.TabIndex = 5;
            this.labely.Text = "labely";
            // 
            // panel1
            // 
            this.panel1.Location = new System.Drawing.Point(99, 45);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(585, 272);
            this.panel1.TabIndex = 6;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.labely);
            this.Controls.Add(this.labelx);
            this.Controls.Add(this.buttonCurve);
            this.Controls.Add(this.buttonHistogram);
            this.Controls.Add(this.buttonCircle);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        

        #endregion

        private System.Windows.Forms.Button buttonCircle;
        private System.Windows.Forms.Button buttonHistogram;
        private System.Windows.Forms.Button buttonCurve;
        private System.Windows.Forms.Label labelx;
        private System.Windows.Forms.Label labely;
        private System.Windows.Forms.Panel panel1;
    }
}

