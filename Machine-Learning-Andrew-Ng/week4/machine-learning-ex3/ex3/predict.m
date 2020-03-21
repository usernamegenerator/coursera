function p = predict(Theta1, Theta2, X)
%PREDICT Predict the label of an input given a trained neural network
%   p = PREDICT(Theta1, Theta2, X) outputs the predicted label of X given the
%   trained weights of a neural network (Theta1, Theta2)

% Useful values
m = size(X, 1);
num_labels = size(Theta2, 1);

% You need to return the following variables correctly 
p = zeros(size(X, 1), 1);

% ====================== YOUR CODE HERE ======================
% Instructions: Complete the following code to make predictions using
%               your learned neural network. You should set p to a 
%               vector containing labels between 1 to num_labels.
%
% Hint: The max function might come in useful. In particular, the max
%       function can also return the index of the max element, for more
%       information see 'help max'. If your examples are in rows, then, you
%       can use max(A, [], 2) to obtain the max for each row.
%


% X is 5000 sample, 400 features each
% Theta 1 is to the 400 features, feed to next level 25 features
% Theta 2 is to the 25 features, feed to next level 10 h
% the 10 h only has 1 h predicted as 1, rest are 0
% take the index of the 1, that's the prediction

disp(size(Theta1))
disp(size(Theta2))
disp(size(X))

% fill x0 to 1 first
x0 = ones(m,1);

X_prime = [x0 X];



a1 = sigmoid(X_prime * Theta1');

a1_prime = [x0 a1];
h = sigmoid(a1_prime * Theta2');



[ones,p]=max(h,[],2);





% =========================================================================


end
