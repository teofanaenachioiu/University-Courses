namespace CinemaCity
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
            Microsoft.Win32.SystemEvents.UserPreferenceChanged
            -= new Microsoft.Win32.UserPreferenceChangedEventHandler(
            this.UserPreferenceChanged);
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.numeTextBox = new System.Windows.Forms.TextBox();
            this.comandaButton = new System.Windows.Forms.RadioButton();
            this.nextButton = new System.Windows.Forms.Button();
            this.theMainMenu = new System.Windows.Forms.MenuStrip();
            this.fileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.closeToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.settingsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.biggerFontSizeToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.smallerFontSizeToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.backgroundColorToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.fontColorToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.numeLabel = new System.Windows.Forms.Label();
            this.filmLabel = new System.Windows.Forms.Label();
            this.rezervaButton = new System.Windows.Forms.RadioButton();
            this.prenumeLabel = new System.Windows.Forms.Label();
            this.clientLabel = new System.Windows.Forms.Label();
            this.prenumeTextBox = new System.Windows.Forms.TextBox();
            this.actiuneLabel = new System.Windows.Forms.Label();
            this.filmList = new System.Windows.Forms.ListBox();
            this.locuriTextBox = new System.Windows.Forms.TextBox();
            this.locuriLabel = new System.Windows.Forms.Label();
            this.cinemaLabel = new System.Windows.Forms.Label();
            this.theMainMenu.SuspendLayout();
            this.SuspendLayout();
            // 
            // numeTextBox
            // 
            this.numeTextBox.AccessibleDescription = "A text field used for saving the name of the customer";
            this.numeTextBox.AccessibleName = "Customer Name";
            this.numeTextBox.AccessibleRole = System.Windows.Forms.AccessibleRole.Text;
            this.numeTextBox.Location = new System.Drawing.Point(433, 177);
            this.numeTextBox.Margin = new System.Windows.Forms.Padding(4);
            this.numeTextBox.Name = "numeTextBox";
            this.numeTextBox.Size = new System.Drawing.Size(155, 23);
            this.numeTextBox.TabIndex = 5;
            // 
            // comandaButton
            // 
            this.comandaButton.AccessibleDescription = "A radio button used for selecting the action type";
            this.comandaButton.AccessibleName = "Action type";
            this.comandaButton.AccessibleRole = System.Windows.Forms.AccessibleRole.RadioButton;
            this.comandaButton.AutoSize = true;
            this.comandaButton.Location = new System.Drawing.Point(263, 319);
            this.comandaButton.Margin = new System.Windows.Forms.Padding(4);
            this.comandaButton.Name = "comandaButton";
            this.comandaButton.Size = new System.Drawing.Size(86, 21);
            this.comandaButton.TabIndex = 4;
            this.comandaButton.Text = "Comanda";
            this.comandaButton.UseVisualStyleBackColor = true;
            // 
            // nextButton
            // 
            this.nextButton.AccessibleDescription = "A button used for placing a pizza order";
            this.nextButton.AccessibleName = "Order";
            this.nextButton.Location = new System.Drawing.Point(464, 299);
            this.nextButton.Margin = new System.Windows.Forms.Padding(4);
            this.nextButton.Name = "nextButton";
            this.nextButton.Size = new System.Drawing.Size(114, 34);
            this.nextButton.TabIndex = 7;
            this.nextButton.Text = "Next";
            this.nextButton.UseVisualStyleBackColor = true;
            // 
            // theMainMenu
            // 
            this.theMainMenu.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.theMainMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileToolStripMenuItem,
            this.settingsToolStripMenuItem});
            this.theMainMenu.Location = new System.Drawing.Point(0, 0);
            this.theMainMenu.Name = "theMainMenu";
            this.theMainMenu.Padding = new System.Windows.Forms.Padding(10, 2, 0, 2);
            this.theMainMenu.Size = new System.Drawing.Size(635, 24);
            this.theMainMenu.TabIndex = 5;
            this.theMainMenu.Text = "menuStrip1";
            // 
            // fileToolStripMenuItem
            // 
            this.fileToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.closeToolStripMenuItem});
            this.fileToolStripMenuItem.Name = "fileToolStripMenuItem";
            this.fileToolStripMenuItem.Size = new System.Drawing.Size(37, 20);
            this.fileToolStripMenuItem.Text = "File";
            // 
            // closeToolStripMenuItem
            // 
            this.closeToolStripMenuItem.Name = "closeToolStripMenuItem";
            this.closeToolStripMenuItem.Size = new System.Drawing.Size(103, 22);
            this.closeToolStripMenuItem.Text = "Close";
            this.closeToolStripMenuItem.Click += new System.EventHandler(this.CloseToolStripMenuItem_Click);
            // 
            // settingsToolStripMenuItem
            // 
            this.settingsToolStripMenuItem.AccessibleDescription = "A button for settings";
            this.settingsToolStripMenuItem.AccessibleName = "Settings";
            this.settingsToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.biggerFontSizeToolStripMenuItem,
            this.smallerFontSizeToolStripMenuItem,
            this.backgroundColorToolStripMenuItem,
            this.fontColorToolStripMenuItem});
            this.settingsToolStripMenuItem.Name = "settingsToolStripMenuItem";
            this.settingsToolStripMenuItem.Size = new System.Drawing.Size(61, 20);
            this.settingsToolStripMenuItem.Text = "Settings";
            // 
            // biggerFontSizeToolStripMenuItem
            // 
            this.biggerFontSizeToolStripMenuItem.Name = "biggerFontSizeToolStripMenuItem";
            this.biggerFontSizeToolStripMenuItem.Size = new System.Drawing.Size(170, 22);
            this.biggerFontSizeToolStripMenuItem.Text = "Bigger Font Size";
            this.biggerFontSizeToolStripMenuItem.Click += new System.EventHandler(this.BiggerFontSizeToolStripMenuItem_Click);
            // 
            // smallerFontSizeToolStripMenuItem
            // 
            this.smallerFontSizeToolStripMenuItem.Name = "smallerFontSizeToolStripMenuItem";
            this.smallerFontSizeToolStripMenuItem.Size = new System.Drawing.Size(170, 22);
            this.smallerFontSizeToolStripMenuItem.Text = "Smaller Font Size";
            this.smallerFontSizeToolStripMenuItem.Click += new System.EventHandler(this.SmallerFontSizeToolStripMenuItem_Click);
            // 
            // backgroundColorToolStripMenuItem
            // 
            this.backgroundColorToolStripMenuItem.Name = "backgroundColorToolStripMenuItem";
            this.backgroundColorToolStripMenuItem.Size = new System.Drawing.Size(170, 22);
            this.backgroundColorToolStripMenuItem.Text = "Background Color";
            this.backgroundColorToolStripMenuItem.Click += new System.EventHandler(this.BackgroundColorToolStripMenuItem_Click);
            // 
            // fontColorToolStripMenuItem
            // 
            this.fontColorToolStripMenuItem.Name = "fontColorToolStripMenuItem";
            this.fontColorToolStripMenuItem.Size = new System.Drawing.Size(170, 22);
            this.fontColorToolStripMenuItem.Text = "Font Color";
            this.fontColorToolStripMenuItem.Click += new System.EventHandler(this.FontColorToolStripMenuItem_Click);
            // 
            // numeLabel
            // 
            this.numeLabel.AutoSize = true;
            this.numeLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.numeLabel.Location = new System.Drawing.Point(430, 156);
            this.numeLabel.Name = "numeLabel";
            this.numeLabel.Size = new System.Drawing.Size(45, 17);
            this.numeLabel.TabIndex = 2;
            this.numeLabel.Text = "&Nume";
            // 
            // filmLabel
            // 
            this.filmLabel.AutoSize = true;
            this.filmLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.filmLabel.Location = new System.Drawing.Point(43, 115);
            this.filmLabel.Name = "filmLabel";
            this.filmLabel.Size = new System.Drawing.Size(37, 17);
            this.filmLabel.TabIndex = 5;
            this.filmLabel.Text = "&Film";
            // 
            // rezervaButton
            // 
            this.rezervaButton.AccessibleDescription = "A radio button used for selecting the action type";
            this.rezervaButton.AccessibleName = "Action type reserve";
            this.rezervaButton.AccessibleRole = System.Windows.Forms.AccessibleRole.RadioButton;
            this.rezervaButton.AutoSize = true;
            this.rezervaButton.Checked = true;
            this.rezervaButton.Location = new System.Drawing.Point(263, 288);
            this.rezervaButton.Margin = new System.Windows.Forms.Padding(4);
            this.rezervaButton.Name = "rezervaButton";
            this.rezervaButton.Size = new System.Drawing.Size(79, 21);
            this.rezervaButton.TabIndex = 3;
            this.rezervaButton.TabStop = true;
            this.rezervaButton.Text = "Rezerva";
            this.rezervaButton.UseVisualStyleBackColor = true;
            // 
            // prenumeLabel
            // 
            this.prenumeLabel.AutoSize = true;
            this.prenumeLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.prenumeLabel.Location = new System.Drawing.Point(430, 215);
            this.prenumeLabel.Name = "prenumeLabel";
            this.prenumeLabel.Size = new System.Drawing.Size(65, 17);
            this.prenumeLabel.TabIndex = 9;
            this.prenumeLabel.Text = "&Prenume";
            // 
            // clientLabel
            // 
            this.clientLabel.AutoSize = true;
            this.clientLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.clientLabel.Location = new System.Drawing.Point(430, 115);
            this.clientLabel.Name = "clientLabel";
            this.clientLabel.Size = new System.Drawing.Size(86, 17);
            this.clientLabel.TabIndex = 0;
            this.clientLabel.Text = "Date client";
            // 
            // prenumeTextBox
            // 
            this.prenumeTextBox.AccessibleDescription = "A text field used for saving the name of the customer";
            this.prenumeTextBox.AccessibleName = "Customer Name";
            this.prenumeTextBox.AccessibleRole = System.Windows.Forms.AccessibleRole.Text;
            this.prenumeTextBox.Location = new System.Drawing.Point(433, 238);
            this.prenumeTextBox.Margin = new System.Windows.Forms.Padding(4);
            this.prenumeTextBox.Name = "prenumeTextBox";
            this.prenumeTextBox.Size = new System.Drawing.Size(155, 23);
            this.prenumeTextBox.TabIndex = 6;
            // 
            // actiuneLabel
            // 
            this.actiuneLabel.AutoSize = true;
            this.actiuneLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.actiuneLabel.Location = new System.Drawing.Point(260, 244);
            this.actiuneLabel.Name = "actiuneLabel";
            this.actiuneLabel.Size = new System.Drawing.Size(62, 17);
            this.actiuneLabel.TabIndex = 11;
            this.actiuneLabel.Text = "&Actiune";
            // 
            // filmList
            // 
            this.filmList.AccessibleDescription = "A list box for showing the movies";
            this.filmList.AccessibleName = "Movie list";
            this.filmList.FormattingEnabled = true;
            this.filmList.ItemHeight = 16;
            this.filmList.Location = new System.Drawing.Point(46, 153);
            this.filmList.Name = "filmList";
            this.filmList.Size = new System.Drawing.Size(141, 196);
            this.filmList.TabIndex = 1;
            // 
            // locuriTextBox
            // 
            this.locuriTextBox.AccessibleDescription = "A text field used for saving the number of places";
            this.locuriTextBox.AccessibleName = "Movie reserved places";
            this.locuriTextBox.Location = new System.Drawing.Point(263, 153);
            this.locuriTextBox.Name = "locuriTextBox";
            this.locuriTextBox.Size = new System.Drawing.Size(48, 23);
            this.locuriTextBox.TabIndex = 2;
            // 
            // locuriLabel
            // 
            this.locuriLabel.AutoSize = true;
            this.locuriLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.locuriLabel.Location = new System.Drawing.Point(260, 115);
            this.locuriLabel.Name = "locuriLabel";
            this.locuriLabel.Size = new System.Drawing.Size(100, 17);
            this.locuriLabel.TabIndex = 15;
            this.locuriLabel.Text = "&Numar locuri";
            // 
            // cinemaLabel
            // 
            this.cinemaLabel.AutoSize = true;
            this.cinemaLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cinemaLabel.Location = new System.Drawing.Point(46, 45);
            this.cinemaLabel.Name = "cinemaLabel";
            this.cinemaLabel.Size = new System.Drawing.Size(136, 26);
            this.cinemaLabel.TabIndex = 16;
            this.cinemaLabel.Text = "CinemaCity";
            // 
            // Form1
            // 
            this.AccessibleDescription = "Cinema Name";
            this.AccessibleName = "Cinema Name";
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(635, 378);
            this.Controls.Add(this.cinemaLabel);
            this.Controls.Add(this.locuriLabel);
            this.Controls.Add(this.locuriTextBox);
            this.Controls.Add(this.filmList);
            this.Controls.Add(this.actiuneLabel);
            this.Controls.Add(this.prenumeTextBox);
            this.Controls.Add(this.clientLabel);
            this.Controls.Add(this.prenumeLabel);
            this.Controls.Add(this.rezervaButton);
            this.Controls.Add(this.filmLabel);
            this.Controls.Add(this.numeLabel);
            this.Controls.Add(this.nextButton);
            this.Controls.Add(this.comandaButton);
            this.Controls.Add(this.numeTextBox);
            this.Controls.Add(this.theMainMenu);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.HelpButton = true;
            this.KeyPreview = true;
            this.MainMenuStrip = this.theMainMenu;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "Form1";
            this.Text = "CinemaCity";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.theMainMenu.ResumeLayout(false);
            this.theMainMenu.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox numeTextBox;
        private System.Windows.Forms.RadioButton comandaButton;
        private System.Windows.Forms.Button nextButton;
        private System.Windows.Forms.MenuStrip theMainMenu;
        private System.Windows.Forms.ToolStripMenuItem settingsToolStripMenuItem;
        private System.Windows.Forms.Label numeLabel;
        private System.Windows.Forms.Label filmLabel;
        private System.Windows.Forms.RadioButton rezervaButton;
        private System.Windows.Forms.ToolStripMenuItem biggerFontSizeToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem smallerFontSizeToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem backgroundColorToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem fontColorToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem fileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem closeToolStripMenuItem;
        private System.Windows.Forms.Label prenumeLabel;
        private System.Windows.Forms.Label clientLabel;
        private System.Windows.Forms.TextBox prenumeTextBox;
        private System.Windows.Forms.Label actiuneLabel;
        private System.Windows.Forms.ListBox filmList;
        private System.Windows.Forms.TextBox locuriTextBox;
        private System.Windows.Forms.Label locuriLabel;
        private System.Windows.Forms.Label cinemaLabel;
    }
}

