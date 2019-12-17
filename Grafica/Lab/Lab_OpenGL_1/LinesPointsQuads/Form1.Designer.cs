namespace LinesPointsQuads
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
            this.components = new System.ComponentModel.Container();
            this.glControl1 = new OpenTK.GLControl();
            this.comboBox1 = new MetroFramework.Controls.MetroComboBox();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.metroLabelRed = new System.Windows.Forms.Label();
            this.trackBarRed = new MetroFramework.Controls.MetroTrackBar();
            this.textBoxRed = new MetroFramework.Controls.MetroTextBox();
            this.textBoxGreen = new MetroFramework.Controls.MetroTextBox();
            this.trackBarGreen = new MetroFramework.Controls.MetroTrackBar();
            this.metroLabelGreen = new System.Windows.Forms.Label();
            this.textBoxBlue = new MetroFramework.Controls.MetroTextBox();
            this.trackBarBlue = new MetroFramework.Controls.MetroTrackBar();
            this.metroLabel3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // glControl1
            // 
            this.glControl1.BackColor = System.Drawing.Color.Black;
            this.glControl1.Location = new System.Drawing.Point(23, 63);
            this.glControl1.Name = "glControl1";
            this.glControl1.Size = new System.Drawing.Size(312, 337);
            this.glControl1.TabIndex = 0;
            this.glControl1.VSync = false;
            this.glControl1.Load += new System.EventHandler(this.glControl1_Load);
            this.glControl1.Paint += new System.Windows.Forms.PaintEventHandler(this.glControl1_Paint);
            // 
            // comboBox1
            // 
            this.comboBox1.DisplayMember = "0";
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.ItemHeight = 23;
            this.comboBox1.Items.AddRange(new object[] {
            "Points",
            "Lines",
            "Triangles",
            "Quad"});
            this.comboBox1.Location = new System.Drawing.Point(355, 63);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.PromptText = "Select";
            this.comboBox1.Size = new System.Drawing.Size(147, 29);
            this.comboBox1.TabIndex = 1;
            this.comboBox1.UseSelectable = true;
            // 
            // timer1
            // 
            this.timer1.Enabled = true;
            this.timer1.Interval = 30;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // metroLabelRed
            // 
            this.metroLabelRed.AutoSize = true;
            this.metroLabelRed.Location = new System.Drawing.Point(414, 112);
            this.metroLabelRed.Name = "metroLabelRed";
            this.metroLabelRed.Size = new System.Drawing.Size(15, 13);
            this.metroLabelRed.TabIndex = 2;
            this.metroLabelRed.Text = "R";
            // 
            // trackBarRed
            // 
            this.trackBarRed.BackColor = System.Drawing.Color.Transparent;
            this.trackBarRed.Location = new System.Drawing.Point(355, 134);
            this.trackBarRed.Maximum = 255;
            this.trackBarRed.Name = "trackBarRed";
            this.trackBarRed.Size = new System.Drawing.Size(107, 23);
            this.trackBarRed.TabIndex = 3;
            this.trackBarRed.Text = "metroTrackBar1";
            this.trackBarRed.Value = 255;
            this.trackBarRed.ValueChanged += new System.EventHandler(this.trackBarRed_ValueChanged);
            // 
            // textBoxRed
            // 
            // 
            // 
            // 
            this.textBoxRed.CustomButton.Image = null;
            this.textBoxRed.CustomButton.Location = new System.Drawing.Point(12, 1);
            this.textBoxRed.CustomButton.Name = "";
            this.textBoxRed.CustomButton.Size = new System.Drawing.Size(21, 21);
            this.textBoxRed.CustomButton.Style = MetroFramework.MetroColorStyle.Blue;
            this.textBoxRed.CustomButton.TabIndex = 1;
            this.textBoxRed.CustomButton.Theme = MetroFramework.MetroThemeStyle.Light;
            this.textBoxRed.CustomButton.UseSelectable = true;
            this.textBoxRed.CustomButton.Visible = false;
            this.textBoxRed.Lines = new string[] {
        "255"};
            this.textBoxRed.Location = new System.Drawing.Point(468, 134);
            this.textBoxRed.MaxLength = 32767;
            this.textBoxRed.Name = "textBoxRed";
            this.textBoxRed.PasswordChar = '\0';
            this.textBoxRed.ReadOnly = true;
            this.textBoxRed.ScrollBars = System.Windows.Forms.ScrollBars.None;
            this.textBoxRed.SelectedText = "";
            this.textBoxRed.SelectionLength = 0;
            this.textBoxRed.SelectionStart = 0;
            this.textBoxRed.ShortcutsEnabled = true;
            this.textBoxRed.Size = new System.Drawing.Size(34, 23);
            this.textBoxRed.TabIndex = 4;
            this.textBoxRed.Text = "255";
            this.textBoxRed.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.textBoxRed.UseSelectable = true;
            this.textBoxRed.WaterMarkColor = System.Drawing.Color.FromArgb(((int)(((byte)(109)))), ((int)(((byte)(109)))), ((int)(((byte)(109)))));
            this.textBoxRed.WaterMarkFont = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Pixel);
            // 
            // textBoxGreen
            // 
            // 
            // 
            // 
            this.textBoxGreen.CustomButton.Image = null;
            this.textBoxGreen.CustomButton.Location = new System.Drawing.Point(12, 1);
            this.textBoxGreen.CustomButton.Name = "";
            this.textBoxGreen.CustomButton.Size = new System.Drawing.Size(21, 21);
            this.textBoxGreen.CustomButton.Style = MetroFramework.MetroColorStyle.Blue;
            this.textBoxGreen.CustomButton.TabIndex = 1;
            this.textBoxGreen.CustomButton.Theme = MetroFramework.MetroThemeStyle.Light;
            this.textBoxGreen.CustomButton.UseSelectable = true;
            this.textBoxGreen.CustomButton.Visible = false;
            this.textBoxGreen.Lines = new string[] {
        "255"};
            this.textBoxGreen.Location = new System.Drawing.Point(468, 183);
            this.textBoxGreen.MaxLength = 32767;
            this.textBoxGreen.Name = "textBoxGreen";
            this.textBoxGreen.PasswordChar = '\0';
            this.textBoxGreen.ReadOnly = true;
            this.textBoxGreen.ScrollBars = System.Windows.Forms.ScrollBars.None;
            this.textBoxGreen.SelectedText = "";
            this.textBoxGreen.SelectionLength = 0;
            this.textBoxGreen.SelectionStart = 0;
            this.textBoxGreen.ShortcutsEnabled = true;
            this.textBoxGreen.Size = new System.Drawing.Size(34, 23);
            this.textBoxGreen.TabIndex = 7;
            this.textBoxGreen.Text = "255";
            this.textBoxGreen.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.textBoxGreen.UseSelectable = true;
            this.textBoxGreen.WaterMarkColor = System.Drawing.Color.FromArgb(((int)(((byte)(109)))), ((int)(((byte)(109)))), ((int)(((byte)(109)))));
            this.textBoxGreen.WaterMarkFont = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Pixel);
            // 
            // trackBarGreen
            // 
            this.trackBarGreen.BackColor = System.Drawing.Color.Transparent;
            this.trackBarGreen.Location = new System.Drawing.Point(355, 183);
            this.trackBarGreen.Maximum = 255;
            this.trackBarGreen.Name = "trackBarGreen";
            this.trackBarGreen.Size = new System.Drawing.Size(107, 23);
            this.trackBarGreen.TabIndex = 6;
            this.trackBarGreen.Text = "metroTrackBar2";
            this.trackBarGreen.Value = 255;
            this.trackBarGreen.ValueChanged += new System.EventHandler(this.trackBarGreen_ValueChanged);
            // 
            // metroLabelGreen
            // 
            this.metroLabelGreen.AutoSize = true;
            this.metroLabelGreen.Location = new System.Drawing.Point(414, 161);
            this.metroLabelGreen.Name = "metroLabelGreen";
            this.metroLabelGreen.Size = new System.Drawing.Size(15, 13);
            this.metroLabelGreen.TabIndex = 5;
            this.metroLabelGreen.Text = "G";
            // 
            // textBoxBlue
            // 
            // 
            // 
            // 
            this.textBoxBlue.CustomButton.Image = null;
            this.textBoxBlue.CustomButton.Location = new System.Drawing.Point(12, 1);
            this.textBoxBlue.CustomButton.Name = "";
            this.textBoxBlue.CustomButton.Size = new System.Drawing.Size(21, 21);
            this.textBoxBlue.CustomButton.Style = MetroFramework.MetroColorStyle.Blue;
            this.textBoxBlue.CustomButton.TabIndex = 1;
            this.textBoxBlue.CustomButton.Theme = MetroFramework.MetroThemeStyle.Light;
            this.textBoxBlue.CustomButton.UseSelectable = true;
            this.textBoxBlue.CustomButton.Visible = false;
            this.textBoxBlue.Lines = new string[] {
        "255"};
            this.textBoxBlue.Location = new System.Drawing.Point(468, 237);
            this.textBoxBlue.MaxLength = 32767;
            this.textBoxBlue.Name = "textBoxBlue";
            this.textBoxBlue.PasswordChar = '\0';
            this.textBoxBlue.ReadOnly = true;
            this.textBoxBlue.ScrollBars = System.Windows.Forms.ScrollBars.None;
            this.textBoxBlue.SelectedText = "";
            this.textBoxBlue.SelectionLength = 0;
            this.textBoxBlue.SelectionStart = 0;
            this.textBoxBlue.ShortcutsEnabled = true;
            this.textBoxBlue.Size = new System.Drawing.Size(34, 23);
            this.textBoxBlue.TabIndex = 10;
            this.textBoxBlue.Text = "255";
            this.textBoxBlue.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.textBoxBlue.UseSelectable = true;
            this.textBoxBlue.WaterMarkColor = System.Drawing.Color.FromArgb(((int)(((byte)(109)))), ((int)(((byte)(109)))), ((int)(((byte)(109)))));
            this.textBoxBlue.WaterMarkFont = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Pixel);
            // 
            // trackBarBlue
            // 
            this.trackBarBlue.BackColor = System.Drawing.Color.Transparent;
            this.trackBarBlue.Location = new System.Drawing.Point(355, 237);
            this.trackBarBlue.Maximum = 255;
            this.trackBarBlue.Name = "trackBarBlue";
            this.trackBarBlue.Size = new System.Drawing.Size(107, 23);
            this.trackBarBlue.TabIndex = 9;
            this.trackBarBlue.Text = "metroTrackBar3";
            this.trackBarBlue.Value = 255;
            this.trackBarBlue.ValueChanged += new System.EventHandler(this.trackBarBlue_ValueChanged);
            // 
            // metroLabel3
            // 
            this.metroLabel3.AutoSize = true;
            this.metroLabel3.Location = new System.Drawing.Point(414, 215);
            this.metroLabel3.Name = "metroLabel3";
            this.metroLabel3.Size = new System.Drawing.Size(14, 13);
            this.metroLabel3.TabIndex = 8;
            this.metroLabel3.Text = "B";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(18, 23);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(183, 26);
            this.label2.TabIndex = 11;
            this.label2.Text = "Simple graphics";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(525, 423);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.textBoxBlue);
            this.Controls.Add(this.trackBarBlue);
            this.Controls.Add(this.metroLabel3);
            this.Controls.Add(this.textBoxGreen);
            this.Controls.Add(this.trackBarGreen);
            this.Controls.Add(this.metroLabelGreen);
            this.Controls.Add(this.textBoxRed);
            this.Controls.Add(this.trackBarRed);
            this.Controls.Add(this.metroLabelRed);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.glControl1);
            this.Name = "Form1";
            this.Text = "0";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private OpenTK.GLControl glControl1;
        private MetroFramework.Controls.MetroComboBox comboBox1;

        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.Label label2;
  
        private MetroFramework.Controls.MetroTrackBar trackBarRed;
        private MetroFramework.Controls.MetroTextBox textBoxRed;
        private MetroFramework.Controls.MetroTextBox textBoxGreen;

        private MetroFramework.Controls.MetroTrackBar trackBarGreen;
        private MetroFramework.Controls.MetroTextBox textBoxBlue;
        private MetroFramework.Controls.MetroTrackBar trackBarBlue;

        private System.Windows.Forms.Label metroLabelRed;
        private System.Windows.Forms.Label metroLabelGreen;
        private System.Windows.Forms.Label metroLabel3;

    }
}

