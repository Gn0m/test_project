SELECT * FROM TABLE1;
SELECT * FROM TABLE2;

SELECT count(number) as "Количество",NUMBER as "Значения" FROM public.TABLE1 GROUP BY(number) HAVING count(number)>1;

SELECT number as "То что есть в первой" FROM public.TABLE1 EXCEPT SELECT number FROM public.TABLE2;