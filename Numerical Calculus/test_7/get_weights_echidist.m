function w = get_weights_echidist(m)
  w = (-1).^[0:m];
  n_fac = factorial(m);
  to_mul=1;
  to_div=n_fac;
  for i = 1:m
    to_mul=to_mul*i; 
    to_div=to_div/(m-i+1);
    comb = n_fac/to_div;
    %w(i+1)=w(i + 1)*comb/to_mul;
    w(i + 1) =  w(i + 1)*nchoosek(m, i); 
  end
  w = w';
end