function centroids = computeCentroids(X, idx, K)
%COMPUTECENTROIDS returns the new centroids by computing the means of the 
%data points assigned to each centroid.
%   centroids = COMPUTECENTROIDS(X, idx, K) returns the new centroids by 
%   computing the means of the data points assigned to each centroid. It is
%   given a dataset X where each row is a single data point, a vector
%   idx of centroid assignments (i.e. each entry in range [1..K]) for each
%   example, and K, the number of centroids. You should return a matrix
%   centroids, where each row of centroids is the mean of the data points
%   assigned to it.
%

% Useful variables
[m n] = size(X);

% You need to return the following variables correctly.
centroids = zeros(K, n);


% ====================== YOUR CODE HERE ======================
% Instructions: Go over every centroid and compute mean of all points that
%               belong to it. Concretely, the row vector centroids(i, :)
%               should contain the mean of the data points assigned to
%               centroid i.
%
% Note: You can use a for-loop over the centroids to compute this.
%


#disp(size(X)); #300 2
#disp(size(X(1,:))); #300 2
#disp(m); 300
#disp(n); 2
#disp(K); 3
# disp(size(idx)) # 300 1

# 300 x, 3 center


for k = 1:K
  centerKSum = zeros(1,n);
  kCounter = 0;
  for i = 1:m
    if(idx(i)==k)
      centerKSum = centerKSum + X(i,:);
      kCounter = kCounter + 1;
    endif
  endfor
  ave = centerKSum ./ kCounter;
  centroids(k,:) = ave;
endfor



% =============================================================


end

