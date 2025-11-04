package es.unirioja.paw.pagination;

import java.util.Arrays;

public class PageNumberPagination {

    private final int totalCount;

    private final int pageSize;

    private final int totalPages;

    private final int currentPage;

    private final int firstPage = 1;

    /**
     * 
     * @param totalCount Número total de elementos
     * @param pageSize Tamaño de página
     * @param currentPage Número de la página actual
     */
    public PageNumberPagination(int totalCount, int pageSize, int currentPage) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.totalPages = totalCount / pageSize + ((totalCount % pageSize > 0) ? 1 : 0);
        // asegurar que currentPage esta en el intervalo [first, last]
        if (currentPage < first()) {
            this.currentPage = firstPage;
        } else if (currentPage > last()) {
            this.currentPage = last();
        } else {
            this.currentPage = currentPage;
        }
    }

    /**
     * @return Total de elementos
     */
    public int getTotalCount() {
        return this.totalCount;
    }

    /**
     * @return Número total de páginas
     */
    public int getTotalPages() {
        return this.totalPages;
    }

    /**
     * @return Número de elementos por página
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * @return Número de la página actual
     */
    public int getCurrentPage() {
        return currentPage;
    }

    public boolean isFirstPage() {
        return currentPage == first() ? true : false;
    }

    public boolean isLastPage() {
        return currentPage == last() ? true : false;
    }

    /**
     * @return Número de la página siguiente a la actual
     */
    public Integer next() {
        if (isLastPage()) {
            return null;
        }
        return currentPage + 1;
    }

    /**
     * @return Número de la página anterior a la actual
     */
    public Integer previous() {
        if (isFirstPage()) {
            return null;
        }
        return currentPage - 1;
    }

    /**
     * @return Número de la primera página 
     */
    public int first() {
        return firstPage;
    }

    /**
     * @return Número de la última página 
     */
    public int last() {
        return totalPages + firstPage - 1;
    }

    public int[] adyacentes(int paramInt1, int paramInt2) {
        if (paramInt2 < 1) {
            throw new IllegalArgumentException("Valor de \"cuantas\" ilegal: el nde padyacentes debe ser mayor de cero");
        }
        if (paramInt1 < 1 || paramInt1 > this.totalPages) {
            return null;
        }
        int[] arrayOfInt1 = next(paramInt1, (paramInt2 - 1) / 2 + (paramInt2 - 1) % 2);
        int[] arrayOfInt2 = next(paramInt1, -(paramInt2 - 1) / 2);
        int[] arrayOfInt3 = new int[arrayOfInt2.length + 1 + arrayOfInt1.length];
        for (byte b = 0; b < arrayOfInt2.length; b++) {
            arrayOfInt3[b] = arrayOfInt2[arrayOfInt2.length - b - 1];
        }
        arrayOfInt3[arrayOfInt2.length] = paramInt1;
        System.arraycopy(arrayOfInt1, 0, arrayOfInt3, arrayOfInt2.length + 1, arrayOfInt1.length);
        return arrayOfInt3;
    }

    public int[] adyacentes(int paramInt) {
        return adyacentes(paramInt, 5);
    }

    private int[] next(int paramInt1, int paramInt2) {
        int b1 = (paramInt2 >= 0) ? 1 : -1;
        int[] arrayOfInt = new int[paramInt2 * b1];
        for (int b2 = 0; b2 < arrayOfInt.length; b2++) {
            int i = paramInt1 + (b2 + 1) * b1;
            if (i >= 1 && i <= this.totalPages) {
                arrayOfInt[b2] = i;
            } else {
                return Arrays.copyOf(arrayOfInt, b2);
            }
        }
        return arrayOfInt;
    }

}
