#pragma once
#include <iostream>
#include <string>
#include "Codare.h"

using namespace std;

class BST {
	struct node {
		Codare inform = Codare();
		node* left;
		node* right;

	};

	node* root;

	int start = 1;

	node* makeEmpty(node* t) {
		if (t == NULL)
			return NULL;
		{
			makeEmpty(t->left);
			makeEmpty(t->right);
			delete t;
		}
		return NULL;
	}

	node* insert(Codare x, node* t)
	{
		if (t == NULL)
		{
			t = new node;
			t->inform = x;
			t->left = t->right = NULL;
		}
		else if (x < t->inform)
			t->left = insert(x, t->left);
		else if (x > t->inform)
			t->right = insert(x, t->right);
		return t;
	}

	node* findMin(node* t)
	{
		if (t == NULL)
			return NULL;
		else if (t->left == NULL)
			return t;
		else
			return findMin(t->left);
	}

	node* findMax(node* t) {
		if (t == NULL)
			return NULL;
		else if (t->right == NULL)
			return t;
		else
			return findMax(t->right);
	}

	node* remove(Codare cod, node* t) {
		node* temp;
		if (t == NULL)
			return NULL;
		else if (cod < t->inform)
			t->left = remove(cod, t->left);
		else if (cod > t->inform)
			t->right = remove(cod, t->right);
		else if (t->left && t->right)
		{
			temp = findMin(t->right);
			t->inform = temp->inform;
			t->right = remove(t->inform, t->right);
		}
		else
		{
			temp = t;
			if (t->left == NULL)
				t = t->right;
			else if (t->right == NULL)
				t = t->left;
			delete temp;
		}

		return t;
	}

	void inorder(ostream& out, node* t) {
		if (t == NULL)
			return;
		inorder(out, t->left);
		out << t->inform;
		inorder(out, t->right);
	}

	node* find(node* t, string x) {
		if (t == NULL)
			return NULL;
		else if (x < t->inform.dataCod)
			return find(t->left, x);
		else if (x > t->inform.dataCod)
			return find(t->right, x);
		else
			return t;
	}

public:
	BST() {
		root = NULL;
	}

	~BST() {
		root = makeEmpty(root);
	}

	int insert(string dataCod) {
		Codare codare = Codare();
		codare.cod = start;
		codare.dataCod = dataCod;
		root = insert(codare, root);
		start++;
		return codare.cod;
	}

	void insert(Codare codare) {
		root = insert(codare, root);
	}

	void remove(Codare x) {
		root = remove(x, root);
	}

	void display(ostream& out) {
		inorder(out, root);

	}

	int find(string x) {
		node* rez = find(root, x);
		return rez == NULL ? 0 : rez->inform.cod;
	}
};

