#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
int main()
{
    int n;
    scanf("%d", &n);
    int v[n];
    for (int i = 0; i < n; i++) {
        scanf("%d", &v[i]);
        fprintf(stderr,"%d\n",v[i]);
    }
    int result = 0;
    int goingUp = 0;
    int currHigh= v[0];
    int flat = 1;
    
    for (int i = 0; i+1 < n; i++) {
        fprintf(stderr,"result: %d | goingUp = %d | currHigh = %d | flat = %d\n",result,goingUp,currHigh,flat);
        if (flat == 1) {
            if (v[i] < v[i+1]) {
                flat = 0;
                goingUp = 1;
                if (currHigh < v[i+1]) {
                    currHigh = v[i+1];
                }
            }
            else if (v[i] > v[i+1]) {
                int temp = v[i+1] - currHigh;
                if (temp < result) {
                    result = temp;
                }
            }
        }
        else if (goingUp == 0) {
            if (v[i] < v[i+1]) {
                goingUp = 1;
                if (currHigh < v[i+1]) {
                    currHigh = v[i+1];
                }
            }
            else if (v[i] > v[i+1]) {
                int temp = v[i+1] - currHigh;
                if (temp < result) {
                    result = temp;
                }
            }
            else {
                flat = 1;
            }
        }
        else {
            if (v[i] < v[i+1]) {
                if (currHigh < v[i+1]) {
                    currHigh = v[i+1];
                }
            }
            else if (v[i] > v[i+1]) {
                int temp = v[i+1] - currHigh;
                if (temp < result) {
                    result = temp;
                }
            }
            else {
                flat = 1;
            }
        }
    }
    

    // Write an action using printf(). DON'T FORGET THE TRAILING \n
    // To debug: fprintf(stderr, "Debug messages...\n");

    printf("%d\n",result);

    return 0;
}