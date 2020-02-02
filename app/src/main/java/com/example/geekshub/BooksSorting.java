package com.example.geekshub;

import com.example.geekshub.data.BookModel;

import java.util.ArrayList;
import java.util.List;

public class BooksSorting {
    public static void sortArrayList(List<BookModel> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (arrayList.get(j).getPrice() < arrayList.get(i).getPrice()) {
                    BookModel temp = arrayList.get(j);
                    arrayList.set(j, arrayList.get(i));
                    arrayList.set(i, temp);

                }
            }

        }
    }}

