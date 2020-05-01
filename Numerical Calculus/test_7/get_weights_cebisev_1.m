function w = get_weights_cebisev_1(m)
    w = (-1).^[0:m].*sin((2.*[0:m]+1)*pi/(2*m+2));
end