package com.example.geekshub.dataStructuresAndAlgorithims;

import com.example.geekshub.data.BookModel;

import java.util.ArrayList;
import java.util.List;

public class BooksSorting {

    //buuble sort Algo------------------------------------------------------------------
    //ascending

    public static void BubblesortArrayListDescending (List<BookModel> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (arrayList.get(j).getPrice() > arrayList.get(i).getPrice()) {
                    BookModel temp = arrayList.get(j);
                    arrayList.set(j, arrayList.get(i));
                    arrayList.set(i, temp);

                }
            }

        }
    }
    //descending
    public static void BubblesortArrayList(List <BookModel> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (arrayList.get(j).getPrice() < arrayList.get(i).getPrice()) {
                    BookModel temp = arrayList.get(j);
                    arrayList.set(j, arrayList.get(i));
                    arrayList.set(i, temp);

                }
            }

        }
    }
//------------------------------------------------------------------------------------

//merge sort algo
    //ascending

    private static List<BookModel> Ascaux;
    public static void AscendingMergeSort(List<BookModel> a) {
        Ascaux = new ArrayList<>(a.size());
        Ascaux.addAll(a);
        AscendingMergeSort(a, 0, a.size() - 1);
    }
    private static void AscendingMergeSort(List<BookModel> a, int lo, int hi) {
        if (lo>=hi) return;
        int mid = lo + (hi-lo) / 2;
        AscendingMergeSort(a, lo, mid);
        AscendingMergeSort(a, mid + 1, hi);
        AscendingMerge(a, lo, mid, hi);
    }

    private static void AscendingMerge(List<BookModel> a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid+1;
        for (int k = lo; k <=hi ; k++)Ascaux.set(k,a.get(k));
        for (int k = lo; k <= hi; k++) {
            if(i>mid) a.set(k,Ascaux.get(j++));
            else if (j>hi) a.set(k,Ascaux.get(i++));
            else if (Ascaux.get(j).getPrice()<Ascaux.get(i).getPrice()) a.set(k,Ascaux.get(j++));
            else a.set(k,Ascaux.get(i++));
        }
    }
//-----------------------------------------------------------------------------------------
    //Descending mergesort

    private static ArrayList<BookModel> Desaux;
    public static void DesMergeSort(List<BookModel> a) {
        Desaux = new ArrayList<>(a.size());
        Desaux.addAll(a);
        DesMergeSort(a, 0, a.size() - 1);
    }
    private static void DesMergeSort(List<BookModel> a, int lo, int hi) {
        if (lo>=hi) return;
        int mid = lo + (hi-lo) / 2;
        DesMergeSort(a, lo, mid);
        DesMergeSort(a, mid + 1, hi);
        DesMerge(a, lo, mid, hi);
    }
    private static void DesMerge(List<BookModel> a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid+1;
        for (int k = lo; k <=hi ; k++) Desaux.set(k,a.get(k));
        for (int k = lo; k <= hi; k++) {
            if(i>mid) a.set(k,Desaux.get(j++));
            else if (j>hi) a.set(k,Desaux.get(i++));
            else if (Desaux.get(j).getPrice()>Desaux.get(i).getPrice()) a.set(k,Desaux.get(j++));
            else a.set(k,Desaux.get(i++));
        }
    }

}

