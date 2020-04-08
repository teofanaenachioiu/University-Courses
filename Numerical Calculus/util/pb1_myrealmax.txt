function  g = myrealmax()
    b=2^10; 
    while(~isinf(b))
        bv=b;
        b=2*b;
    end
    g = (2-eps)*bv;