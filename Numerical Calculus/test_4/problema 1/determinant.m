function d = determinant(A) 
   [n,n] = size(A); 
   if n == 2
      d =A(1,1) * A(2,2) - A(2,1) * A(1,2);
   else
       if n==3
          d = A(1, 1)*A(2, 2)*A(3, 3)+A(2, 1)*A(3, 2)*A(1, 3)+A(1, 2)*A(2, 3)*A(3, 1)-A(1, 3)*A(2, 2)*A(3, 1)-A(1, 1)*A(2, 3)*A(3, 2)-A(2, 1)*A(1, 2)*A(3, 3);
       else
           d = det(A);
       end
   end