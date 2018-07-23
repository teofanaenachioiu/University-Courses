/********************************************************************************
** Form generated from reading UI file 'Desene.ui'
**
** Created by: Qt User Interface Compiler version 5.11.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_DESENE_H
#define UI_DESENE_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_DeseneClass
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *DeseneClass)
    {
        if (DeseneClass->objectName().isEmpty())
            DeseneClass->setObjectName(QStringLiteral("DeseneClass"));
        DeseneClass->resize(600, 400);
        menuBar = new QMenuBar(DeseneClass);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        DeseneClass->setMenuBar(menuBar);
        mainToolBar = new QToolBar(DeseneClass);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        DeseneClass->addToolBar(mainToolBar);
        centralWidget = new QWidget(DeseneClass);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        DeseneClass->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(DeseneClass);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        DeseneClass->setStatusBar(statusBar);

        retranslateUi(DeseneClass);

        QMetaObject::connectSlotsByName(DeseneClass);
    } // setupUi

    void retranslateUi(QMainWindow *DeseneClass)
    {
        DeseneClass->setWindowTitle(QApplication::translate("DeseneClass", "Desene", nullptr));
    } // retranslateUi

};

namespace Ui {
    class DeseneClass: public Ui_DeseneClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_DESENE_H
