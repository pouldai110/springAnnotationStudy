package com.study.spring.base.filter;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyComponentFliter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        System.out.println("annotationMetadata:"+annotationMetadata.getAnnotationTypes().toString());
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        System.out.println("classMetadata:"+classMetadata.getClassName());
        Resource resource = metadataReader.getResource();
        System.out.println("resource："+resource.getFilename());
        return false;
    }
}
