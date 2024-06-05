#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>
#include <unistd.h>

int sizeGlobal = 0;

int **parse(char *where)
{
    int **tab = NULL;
    FILE *file = fopen(where, "r");
    if (file == NULL)
    {
        perror("open");
        return NULL;
    }
 
    char buffer[1024];
    if (fgets(buffer, 1024, file) == NULL)
    {
        perror("fgets");
        return NULL;
    }
    sizeGlobal = atoi(buffer);
    tab = (int **)malloc(sizeGlobal * sizeof(int *));
    if (tab == NULL)
    {
        perror("malloc");
        return NULL;
    }
    
    for (int i = 0; i < sizeGlobal; i++)
    {
        tab[i] = (int *)malloc(2 * sizeof(int));
        if (fgets(buffer, 1024, file) == NULL)
        {
            perror("fgets");
            return NULL;
        }
        char *token = strtok(buffer, " ");
        tab[i][0] = atoi(token);
        token = strtok(NULL, " ");
        tab[i][1] = atoi(token);
        //printf("%d %d\n", tab[i][0], tab[i][1]);
    }
    
    return tab;
}

int nb_gateau_min(int **tab, int taille)
{
    if (taille < 1 || tab == NULL)
    {
        return -1;
    }

    int min = tab[1][1] / tab[1][0];
    for (int i = 0; i < taille; i++)
    {
        if(tab[i][0]==0)
        {
            return -1;
        }
        int tmp = tab[i][1] / tab[i][0];
        if (tmp < min)
        {
            min = tmp;
        }
    }
    return min;
}

int main(int argc, char *argv[])
{
    if (argc != 2)
    {
        return 1;
    }
    int **tab = parse(argv[1]);
    //printf("%d\n", nb_gateau_min(tab, sizeGlobal));
    char *where = strtok(argv[1], ".in");
    strcat(where, ".out");
    int file = open(where, O_WRONLY | O_CREAT, 0644);
    int tmp = nb_gateau_min(tab, sizeGlobal);
    write(file, &tmp, sizeof(int));
    return EXIT_SUCCESS;
}
