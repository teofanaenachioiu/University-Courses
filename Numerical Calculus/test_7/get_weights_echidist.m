function w = get_weights_echidist(m)
  w = (-1).^[0:m];
  n = factorial(m);
  k=1;
  nk=n;
  for i = 1:m
    k=k*i; 
    nk=nk/(m-i+1);
    comb = n/(nk*k);
    w(i+1)=w(i + 1)*comb;
    %w(i + 1) =  w(i + 1)*nchoosek(m, i); 
  end
  w = w';
end