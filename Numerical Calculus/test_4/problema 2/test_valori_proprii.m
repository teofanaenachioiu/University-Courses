n = 7;
A = get_sym_matrix(n);
format long
fct_eig = min(abs(eig(A)))
fct_cu_lup = aprox_vp_lu(A)
fct_fara_lup = aprox_vp(A)

