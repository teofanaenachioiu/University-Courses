/********************************************************************************
** Form generated from reading UI file 'Modele.ui'
**
** Created by: Qt User Interface Compiler version 5.11.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MODELE_H
#define UI_MODELE_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_ModeleClass
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *ModeleClass)
    {
        if (ModeleClass->objectName().isEmpty())
            ModeleClass->setObjectName(QStringLiteral("ModeleClass"));
        ModeleClass->resize(600, 400);
        menuBar = new QMenuBar(ModeleClass);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        ModeleClass->setMenuBar(menuBar);
        mainToolBar = new QToolBar(ModeleClass);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        ModeleClass->addToolBar(mainToolBar);
        centralWidget = new QWidget(ModeleClass);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        ModeleClass->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(ModeleClass);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        ModeleClass->setStatusBar(statusBar);

        retranslateUi(ModeleClass);

        QMetaObject::connectSlotsByName(ModeleClass);
    } // setupUi

    void retranslateUi(QMainWindow *ModeleClass)
    {
        ModeleClass->setWindowTitle(QApplication::translate("ModeleClass", "Modele", nullptr));
    } // retranslateUi

};

namespace Ui {
    class ModeleClass: public Ui_ModeleClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MODELE_H
